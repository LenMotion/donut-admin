package cn.lenmotion.donut.system.remote.impl;

import cn.lenmotion.donut.system.service.SysConfigService;
import cn.lenmotion.donut.system.remote.SysConfigRemoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Service
@AllArgsConstructor
public class SysConfigRemoteServiceImpl implements SysConfigRemoteService {

    private final SysConfigService sysConfigService;

    @Override
    public String getConfigByKey(String configKey) {
        return sysConfigService.getConfigByKey(configKey);
    }

    @Override
    public Integer getConfigIntValue(String configKey) {
        return sysConfigService.getConfigIntValue(configKey);
    }

    @Override
    public Boolean getConfigBoolValue(String configKey) {
        return sysConfigService.getConfigBoolValue(configKey);
    }

}
