package cn.lenmotion.donut.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import cn.lenmotion.donut.auth.entity.LoginBody;
import cn.lenmotion.donut.auth.service.LoginService;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.constants.RedisConstants;
import cn.lenmotion.donut.core.entity.LoginInfo;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.enums.ResponseCodeEnum;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.utils.IpUtils;
import cn.lenmotion.donut.framework.template.JacksonRedisTemplate;
import cn.lenmotion.donut.system.entity.enums.LoginStatusEnum;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.remote.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final RSA loginRsa;
    private final SysUserRemoteService userRemoteService;
    private final SysConfigRemoteService configRemoteService;
    private final SysLoginLogRemoteService loginLogRemoteService;
    private final SysPermissionRemoteService permissionRemoteService;
    private final SysNoticeRemoteService noticeRemoteService;
    private final TaskExecutor taskExecutor;
    private final JacksonRedisTemplate redisTemplate;

    @Override
    public void login(LoginBody loginBody, HttpServletRequest request) {
        Exception exception = null;
        try {
            log.info("start login: {}", JSONUtil.toJsonStr(loginBody));
            // 验证验证码是否正确
            if (configRemoteService.getConfigBoolValue(ConfigConstants.CAPTCHA_ON_OFF)) {
                checkCaptcha(loginBody);
            }
            // 解密密码
            String realPassword = loginRsa.decryptStr(loginBody.getPassword(), KeyType.PrivateKey);
            // 使用用户名和密码进行sha256加密
            String password = SaSecureUtil.sha256BySalt(realPassword, loginBody.getUsername());
            // 查询用户
            SysUser user = userRemoteService.getByUsername(loginBody.getUsername());
            // 判断用户存在与否
            AssertUtils.notNull(user, ResponseCodeEnum.USER_NOT_EXITS);
            // 是否被禁用
            AssertUtils.notEquals(BaseStatusEnum.DISABLE.getCode(), user.getStatus(), ResponseCodeEnum.USER_DISABLED);
            // 判断密码
            AssertUtils.equals(password, user.getPassword(), ResponseCodeEnum.USER_NOT_EXITS);
            // 登录
            StpUtil.login(user.getId());
            // 登陆成功
            this.handleLoginSuccess(user, request);
        } catch (Exception e) {
            if (e instanceof CryptoException) {
                exception = new BusinessException(ResponseCodeEnum.USER_NOT_EXITS);
                throw new BusinessException(ResponseCodeEnum.USER_NOT_EXITS);
            }
            exception = e;
            throw e;
        } finally {
            // 保存登陆信息
            loginLog(loginBody, request, exception);
        }
    }

    @Override
    public void logout() {
        try {
            noticeRemoteService.closeNoticeWebsocket(StpUtil.getTokenInfo());
        } catch (Exception e) {
            log.error("关闭通知websocket异常", e);
        } finally {
            StpUtil.logout();
        }
    }

    /**
     * 校验验证码
     *
     * @param loginBody 登陆信息
     */
    private void checkCaptcha(LoginBody loginBody) {
        AssertUtils.isTrue(StrUtil.isAllNotBlank(loginBody.getCode(), loginBody.getUuid()), "验证码信息不能为空");
        String verifyKey = RedisConstants.CAPTCHA_CODE_KEY + loginBody.getUuid();
        String captcha = redisTemplate.opsForValue().get(verifyKey);
        redisTemplate.delete(verifyKey);
        AssertUtils.notBlank(captcha, "验证码已失效");
        AssertUtils.isTrue(loginBody.getCode().equalsIgnoreCase(captcha), "验证码错误");
    }

    /**
     * 记录登陆日志
     *
     * @param loginBody
     * @param request
     * @param e
     */
    private void loginLog(LoginBody loginBody, HttpServletRequest request, Exception e) {
        String userAgent = request.getHeader(BaseConstants.USER_AGENT_HEADER);
        String ipAddr = IpUtils.getIpAddr(request);

        taskExecutor.execute(() -> {
            try {
                SysLoginLog loginLog = new SysLoginLog();
                loginLog.setStatus(e == null ? LoginStatusEnum.SUCCESS.getCode() : LoginStatusEnum.FAILED.getCode());
                loginLog.setUsername(loginBody.getUsername());
                loginLog.setIp(ipAddr);

                UserAgent agent = UserAgentUtil.parse(userAgent);
                loginLog.setBrowser(agent.getBrowser().getName());
                loginLog.setOs(agent.getOs().getName());
                loginLog.setLoginTime(LocalDateTime.now());
                loginLog.setMsg(Optional.ofNullable(e).map(Exception::getMessage).orElse("登录成功"));
                loginLogRemoteService.saveLoginLog(loginLog);
            } catch (Exception e1) {
                log.error("登录日志记录异常", e1);
            }
        });
    }

    /**
     * 登陆成功
     *
     * @param user
     * @param request
     */
    private void handleLoginSuccess(SysUser user, HttpServletRequest request) {
        SysUser sysUser = new SysUser();
        sysUser.setId(user.getId());
        sysUser.setLoginIp(IpUtils.getIpAddr(request));
        sysUser.setLoginDate(LocalDateTime.now());
        userRemoteService.updateUser(sysUser);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(user.getId());
        loginInfo.setUsername(user.getUsername());
        loginInfo.setDeptAncestors(permissionRemoteService.getDeptAncestors(user.getId()));
        loginInfo.setRoleDataScopes(permissionRemoteService.getRoleDataScope(user.getId()));

        StpUtil.getSession().set(BaseConstants.SESSION_LOGIN_INFO, loginInfo);
    }

}
