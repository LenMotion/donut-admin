package cn.lenmotion.donut.system.remote.impl;

import cn.lenmotion.donut.system.service.SysLoginLogService;
import cn.lenmotion.donut.system.remote.SysLoginLogRemoteService;
import cn.lenmotion.donut.system.entity.po.SysLoginLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysLoginLogRemoteServiceImpl implements SysLoginLogRemoteService {

    private final SysLoginLogService loginLogService;

    @Override
    public boolean saveLoginLog(SysLoginLog loginLog) {
        return loginLogService.save(loginLog);
    }

}
