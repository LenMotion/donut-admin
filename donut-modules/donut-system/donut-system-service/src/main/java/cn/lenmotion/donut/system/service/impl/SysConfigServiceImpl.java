package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.constants.RedisConstants;
import cn.lenmotion.donut.core.context.TenantContext;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.framework.template.JacksonRedisTemplate;
import cn.lenmotion.donut.system.entity.po.SysConfig;
import cn.lenmotion.donut.system.entity.query.ConfigQuery;
import cn.lenmotion.donut.system.entity.vo.LoginPageVO;
import cn.lenmotion.donut.system.mapper.SysConfigMapper;
import cn.lenmotion.donut.system.service.SysConfigService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author lenmotion
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends DonutServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final JacksonRedisTemplate jacksonRedisTemplate;

    @Override
    public JSONObject selectSystemConfigList() {
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysConfig::getSystemConfig, Boolean.TRUE);
        List<SysConfig> list = list(queryWrapper);

        JSONObject result = new JSONObject();

        for (SysConfig config : list) {
            result.put(config.getConfigKey(), config.getConfigValue());
            jacksonRedisTemplate.opsForValue().set(config.getConfigKey(), config.getConfigValue());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSystemConfig(JSONObject data) {
        for (String key : data.keySet()) {
            LambdaUpdateWrapper<SysConfig> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(SysConfig::getConfigKey, key)
                    .set(SysConfig::getConfigValue, data.getString(key));

            super.update(updateWrapper);
            // 删除redis缓存
            jacksonRedisTemplate.delete(this.formatKey(key));
        }
        return true;
    }

    @Override
    public IPage<SysConfig> selectConfigList(ConfigQuery query) {
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.like(StringUtils.isNotBlank(query.getConfigName()), SysConfig::getConfigName, query.getConfigName())
                .eq(ObjUtil.isNotNull(query.getSystemConfig()), SysConfig::getSystemConfig, query.getSystemConfig())
                .like(StrUtil.isNotBlank(query.getConfigKey()), SysConfig::getConfigKey, query.getConfigKey())
                .le(ObjUtil.isNotNull(query.getCreateEndTime()), SysConfig::getCreateTime, query.getCreateEndTime())
                .ge(ObjUtil.isNotNull(query.getCreateStartTime()), SysConfig::getCreateTime, query.getCreateStartTime())
                .orderByDesc(SysConfig::getCreateTime);
        return this.page(query.toPage(), queryWrapper);
    }

    @Override
    public void resetConfigCache() {
        var keys = jacksonRedisTemplate.keys(this.formatKey("*"));
        if (CollectionUtils.isNotEmpty(keys)) {
            keys.forEach(key -> {
                if (StrUtil.isNotBlank(key)) {
                    jacksonRedisTemplate.delete(key);
                }
            });
        }
    }

    @Override
    public String getConfigByKey(String configKey) {
        if (StringUtils.isBlank(configKey)) {
            return null;
        }
        // 获取key
        var key = this.formatKey(configKey);
        // 获取redis缓存数据
        var value = jacksonRedisTemplate.opsForValue().get(key);
        // 如果value为空，去数据库获取
        if (value != null) {
            return value;
        }
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getConfigKey, configKey).last(BaseConstants.LIMIT_1);
        value = Optional.ofNullable(this.getOne(queryWrapper))
                .map(SysConfig::getConfigValue)
                .orElse(StringUtils.EMPTY);
        // 缓存到redis
        jacksonRedisTemplate.opsForValue().set(key, value);
        return value;
    }

    @Override
    public Integer getConfigIntValue(String configKey) {
        return Optional.ofNullable(this.getConfigByKey(configKey))
                .filter(StrUtil::isNotBlank)
                .map(Integer::valueOf)
                .orElse(null);
    }

    @Override
    public Boolean getConfigBoolValue(String configKey) {
        return Optional.ofNullable(this.getConfigByKey(configKey))
                .filter(StrUtil::isNotBlank)
                .map(Boolean::valueOf)
                .orElse(null);
    }

    @Override
    public LoginPageVO getLoginPageConfig() {
        var vo = new LoginPageVO();

        vo.setName(this.getConfigByKey(ConfigConstants.SYSTEM_NAME));
        vo.setTitle(this.getConfigByKey(ConfigConstants.SYSTEM_TITLE));
        vo.setDescription(this.getConfigByKey(ConfigConstants.SYS_DESCRIPTION));
        vo.setLogo(this.getConfigByKey(ConfigConstants.SYSTEM_LOGO));

        return vo;
    }

    @Override
    public boolean saveOrUpdate(SysConfig entity) {
        entity.setSystemConfig(null);
        this.checkConfigKey(entity);

        var result = super.saveOrUpdate(entity);

        if (result) {
            // 重置信息
            jacksonRedisTemplate.opsForValue().set(this.formatKey(entity.getConfigKey()), entity.getConfigValue());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> list) {
        for (Object object : list) {
            var config = this.getById(object.toString());
            if (config == null) {
                continue;
            }
            if (config.getSystemConfig()) {
                throw new BusinessException("系统配置[" + config.getConfigName() + "]不可删除");
            }
            super.removeById(config.getId());
            jacksonRedisTemplate.delete(this.formatKey(config.getConfigKey()));
        }
        return true;
    }

    /**
     * 校验配置键是否重复
     *
     * @param config
     */
    private void checkConfigKey(SysConfig config) {
        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getConfigKey, config.getConfigKey())
                .ne(Objects.nonNull(config.getId()), SysConfig::getId, config.getId())
                .last(BaseConstants.LIMIT_1);
        AssertUtils.isNull(getOne(queryWrapper), "新增配置'" + config.getConfigKey() + "'失败，配置Key已存在");
    }

    /**
     * 格式化key
     */
    private String formatKey(String configKey) {
        return RedisConstants.CONFIG_KEY + TenantContext.getTenant() + ":" + configKey;
    }

}
