package cn.lenmotion.donut.monitor.service;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.lenmotion.donut.core.entity.BasePageQuery;
import cn.lenmotion.donut.core.entity.PageResult;
import cn.lenmotion.donut.framework.template.JacksonRedisTemplate;
import cn.lenmotion.donut.monitor.entity.OnlineUserVO;
import cn.lenmotion.donut.monitor.entity.converter.LoginLogConverter;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import cn.lenmotion.donut.system.remote.SysLoginLogRemoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LenMotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OnlineUserService {

    private final SysLoginLogRemoteService loginLogRemoteService;
    private final RSA loginRsa;
    private final JacksonRedisTemplate redisTemplate;

    /**
     * 获取在线用户信息
     *
     * @param query
     * @return
     */
    public PageResult<OnlineUserVO> getOnlineUserList(BasePageQuery query) {
        List<String> strings = StpUtil.searchTokenValue("", -1, 10, true);

        PageResult<OnlineUserVO> pageResult = new PageResult<>();
        pageResult.setTotal(strings.size());
        pageResult.setPageNum(Convert.toInt(query.getPageNum()));
        pageResult.setPageSize(Convert.toInt(query.getPageSize()));

        var startIndex = Convert.toInt((query.getPageNum() - 1) * query.getPageSize());
        var tokenValueList = StpUtil.searchTokenValue("", startIndex, Convert.toInt(query.getPageSize()), true);

        var loginLogMap = loginLogRemoteService.selectLoginLogByToken(tokenValueList);

        List<OnlineUserVO> result = new ArrayList<>(tokenValueList.size());
        for (String tokenValue : tokenValueList) {
            var realToken = StrUtil.split(tokenValue, StrUtil.COLON).get(3);
            SysLoginLog loginLog = loginLogMap.get(realToken);
            OnlineUserVO onlineUserVO = LoginLogConverter.INSTANCE.toOnlineUserVO(loginLog);
            onlineUserVO.setTimeout(StpUtil.getTokenTimeout(realToken));

            result.add(onlineUserVO);
        }

        pageResult.setItems(result);

        return pageResult;
    }

    /**
     * 强制退出用户
     *
     * @param id
     * @return
     */
    public Boolean kickOutUser(Long id) {
        var loginLog = loginLogRemoteService.getLoginLogById(id);
        if (loginLog != null) {
            var tokenVal = loginRsa.decryptStr(loginLog.getTokenValue(), KeyType.PublicKey);
            StpUtil.kickoutByTokenValue(tokenVal);
            // 直接移除token，避免列表中展示
            StpLogic stpLogic = new StpLogic(StpUtil.TYPE);
            redisTemplate.delete(stpLogic.splicingKeyTokenValue(tokenVal));
        }
        return true;
    }
}
