package cn.lenmotion.donut.system.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.entity.BaseImportResult;
import cn.lenmotion.donut.core.entity.BaseUpdateStatus;
import cn.lenmotion.donut.core.enums.BaseStatusEnum;
import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.core.utils.AopUtils;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.framework.excel.ExcelClient;
import cn.lenmotion.donut.system.entity.converter.UserConverter;
import cn.lenmotion.donut.system.entity.po.SysDept;
import cn.lenmotion.donut.system.entity.po.SysPost;
import cn.lenmotion.donut.system.entity.po.SysUser;
import cn.lenmotion.donut.system.entity.po.SysUserDept;
import cn.lenmotion.donut.system.entity.query.PostQuery;
import cn.lenmotion.donut.system.entity.query.UserQuery;
import cn.lenmotion.donut.system.entity.request.SysUserRequest;
import cn.lenmotion.donut.system.entity.request.UserAvatarRequest;
import cn.lenmotion.donut.system.entity.request.UserProfileRequest;
import cn.lenmotion.donut.system.entity.request.UserPwdRequest;
import cn.lenmotion.donut.system.entity.vo.UserDeptVO;
import cn.lenmotion.donut.system.entity.vo.UserResponseVO;
import cn.lenmotion.donut.system.entity.vo.export.UserExportVO;
import cn.lenmotion.donut.system.mapper.SysUserMapper;
import cn.lenmotion.donut.system.remote.SysExportLogRemoteService;
import cn.lenmotion.donut.system.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fhs.trans.service.impl.TransService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends DonutServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysConfigService configService;
    private final SysUserDeptService userDeptService;
    private final SysUserRoleService userRoleService;
    private final SysPostService postService;
    private final SysDeptService deptService;
    private final TransService transService;
    private final RSA loginRsa;
    private final SysExportLogRemoteService exportLogRemoteService;
    private final TaskExecutor taskExecutor;
    private final ExcelClient excelClient;

    @Override
    @DataScope(type = DataScopeTypeEnum.DEPT, deptAlias = "ud", deptField = "dept_id")
    public IPage<UserResponseVO> selectUserPage(UserQuery userQuery) {
        return getBaseMapper().selectUserPage(userQuery.toPage(), userQuery);
    }

    @Override
    @DataScope(type = DataScopeTypeEnum.DEPT, deptAlias = "ud", deptField = "dept_id")
    public List<UserExportVO> selectUserExportList(UserQuery userQuery) {
        return getBaseMapper().selectUserExportList(userQuery);
    }

    @Override
    @DataScope(type = DataScopeTypeEnum.DEPT, deptAlias = "ud", deptField = "dept_id")
    public void exportUserList(UserQuery userQuery) {
        var userId = StpUtil.getLoginIdAsLong();
        taskExecutor.execute(() -> {
            var timer = DateUtil.timer();
            var exportLog = exportLogRemoteService.startExport(userId, "用户信息");
            var userList = getBaseMapper().selectUserExportList(userQuery);
            excelClient.exportTrans(userList, UserExportVO.class, exportLog, timer);
        });
    }

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username)
                .last(BaseConstants.LIMIT_1);
        return super.getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdate(SysUserRequest request) {
        var user = UserConverter.INSTANCE.toPo(request);
        this.checkUser(user);
        // 如果是新增，需要设置默认密码
        if (Objects.isNull(user.getId())) {
            user.setStatus(BaseStatusEnum.DISABLE.getCode());
            var defaultPassword = configService.getConfigByKey(ConfigConstants.USER_DEFAULT_PASSWORD);
            user.setPassword(SaSecureUtil.sha256BySalt(defaultPassword, user.getUsername()));
        } else {
            // 不允许修改用户登录名称
            user.setUsername(null);
        }
        var result = super.saveOrUpdate(user);
        // 更新成功，设置部门与角色
        if (result) {
            // 将默认的部门关联信息也加入到sys_user_dept表
            SysUserDept userDept = new SysUserDept();
            userDept.setDeptId(request.getDeptId());
            userDept.setPostId(request.getPostId());
            userDept.setDefaultDept(true);

            request.getUserDeptList().add(userDept);
            // 新增关联部门
            userDeptService.insertUserDept(user.getId(), request.getUserDeptList());
            // 新增关联角色
            userRoleService.insertUserRole(user.getId(), request.getRoleIds());
        }

        return result;
    }

    @Override
    public UserResponseVO getUserDetail(Long userId) {
        var user = this.getById(userId);
        AssertUtils.notNull(user, "用户不存在");

        var responseVo = UserConverter.INSTANCE.toResponseVO(user);
        // 查询部门信息
        Optional.ofNullable(responseVo.getDeptId())
                .map(deptService::getById)
                .map(SysDept::getDeptName)
                .ifPresent(responseVo::setDeptName);
        // 查询岗位信息
        Optional.ofNullable(responseVo.getPostId())
                .map(postService::getById)
                .map(SysPost::getPostName)
                .ifPresent(responseVo::setPostName);
        // 设置用户关联的角色ID
        responseVo.setRoleIds(userRoleService.getRoleIdsByUserId(userId));
        // 设置用户部门列表
        var deptList = userDeptService.getOtherUserDeptListByUserId(userId);
        PostQuery postQuery = new PostQuery();
        for (UserDeptVO userDeptVO : deptList) {
            postQuery.setDeptId(userDeptVO.getDeptId());
            userDeptVO.setPostOptions(postService.selectDeptPostList(postQuery));
        }
        responseVo.setUserDeptList(deptList);
        transService.transOne(responseVo);
        return responseVo;
    }

    @Override
    public BaseImportResult importUser(List<SysUserRequest> userList) {
        var thisProxy = AopUtils.getAopProxy(this);
        List<String> failedMessage = userList.stream()
                .map(user -> {
                    try {
                        thisProxy.saveOrUpdate(user);
                        return null;
                    } catch (Exception e) {
                        return "第" + (userList.indexOf(user) + 1) + "行导入失败：" + e.getMessage();
                    }
                })
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());

        log.error("导入用户失败：" + failedMessage);

        int success = userList.size() - failedMessage.size();
        int failed = failedMessage.size();

        return new BaseImportResult(userList.size(), success, failed, failedMessage);
    }

    @Override
    public boolean updateStatus(BaseUpdateStatus request) {
        boolean result = super.updateStatus(request);
        // 如果是禁用并且对应用户是登录状态，则同时退出当前用户
        if (result && BaseStatusEnum.DISABLE.getCode().equals(request.getStatus()) && StpUtil.isLogin(request.getId())) {
            StpUtil.logout(request.getId());
        }
        return result;
    }

    @Override
    public Boolean updateProfile(UserProfileRequest request) {
        var user = UserConverter.INSTANCE.toPo(request);
        user.setId(StpUtil.getLoginIdAsLong());

        user.setQuickNav(CollUtil.isEmpty(request.getQuickNav()) ? "" : StrUtil.join(",", request.getQuickNav()));
        // 判断电话是否重复
        if (StrUtil.isNotBlank(user.getPhoneNumber())) {
            super.validColumnUnique(user, SysUser::getPhoneNumber, "电话号码");
        }
        return super.updateById(user);
    }

    @Override
    public Boolean updateAvatar(UserAvatarRequest request) {
        var user = new SysUser();

        user.setAvatar(request.getAvatar());
        user.setId(StpUtil.getLoginIdAsLong());

        return super.updateById(user);
    }

    @Override
    public Boolean updatePassword(UserPwdRequest request) {
        String newPwd, oldPwd;
        try {
            newPwd = loginRsa.decryptStr(request.getNewPassword(), KeyType.PrivateKey);
            oldPwd = loginRsa.decryptStr(request.getOldPassword(), KeyType.PrivateKey);
        } catch (Exception e) {
            log.error("密码解密失败", e);
            throw new BusinessException("密码解密失败");
        }
        var user = this.getById(StpUtil.getLoginIdAsLong());
        // 密码校验
        AssertUtils.equals(SaSecureUtil.sha256BySalt(oldPwd, user.getUsername()), user.getPassword(), "原密码错误");
        // 更新用户密码
        var updateUser = new SysUser();
        updateUser.setId(user.getId());
        updateUser.setPassword(SaSecureUtil.sha256BySalt(newPwd, user.getUsername()));
        return this.updateById(updateUser);
    }

    /**
     * 判断用户信息
     *
     * @param user
     */
    private void checkUser(SysUser user) {
        // 查询名称是否重复
        super.validColumnUnique(user, SysUser::getUsername, "用户名");
        // 判断电话是否重复
        if (StrUtil.isNotBlank(user.getPhoneNumber())) {
            super.validColumnUnique(user, SysUser::getPhoneNumber, "电话号码");
        }
        // 判断编号是否重复
        if (StrUtil.isNotBlank(user.getUserCode())) {
            super.validColumnUnique(user, SysUser::getUserCode, "员工编号");
        }
        // 判断证件编号是否重复
        if (StrUtil.isNotBlank(user.getIdCard())) {
            super.validColumnUnique(user, SysUser::getIdCard, "证件编号");
        }
        // 校验当前用户是否有这个部门的权限
        deptService.checkEditPermission(user.getDeptId(), StpUtil.getLoginIdAsLong());
    }

}
