package cn.lenmotion.donut.system.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.ConfigConstants;
import cn.lenmotion.donut.core.constants.RedisConstants;
import cn.lenmotion.donut.core.exception.BusinessException;
import cn.lenmotion.donut.core.utils.AopUtils;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.core.service.impl.DonutServiceImpl;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author lenmotion
 */
@Service
public class SysConfigServiceImpl extends DonutServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    public JSONObject selectSystemConfigList() {
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysConfig::getSystemConfig, Boolean.TRUE);
        List<SysConfig> list = list(queryWrapper);

        JSONObject result = new JSONObject();

        for (SysConfig config : list) {
            result.put(config.getConfigKey(), config.getConfigValue());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = RedisConstants.CONFIG_KEY, allEntries = true)
    public boolean updateSystemConfig(JSONObject data) {
        for (String key : data.keySet()) {
            LambdaUpdateWrapper<SysConfig> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.eq(SysConfig::getConfigKey, key)
                    .set(SysConfig::getConfigValue, data.getString(key));

            super.update(updateWrapper);
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
    @CacheEvict(value = RedisConstants.CONFIG_KEY, allEntries = true)
    public void resetConfigCache() {
    }

    @Override
    @Cacheable(value = RedisConstants.CONFIG_KEY, key = "#configKey")
    public String getConfigByKey(String configKey) {
        if (StringUtils.isBlank(configKey)) {
            return null;
        }

        LambdaQueryWrapper<SysConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysConfig::getConfigKey, configKey).last(BaseConstants.LIMIT_1);
        return Optional.ofNullable(this.getOne(queryWrapper))
                .map(SysConfig::getConfigValue)
                .orElse(StringUtils.EMPTY);
    }

    @Override
    public Integer getConfigIntValue(String configKey) {
        return Optional.ofNullable(AopUtils.getAopProxy(this).getConfigByKey(configKey))
                .filter(StrUtil::isNotBlank)
                .map(Integer::valueOf)
                .orElse(null);
    }

    @Override
    public Boolean getConfigBoolValue(String configKey) {
        return Optional.ofNullable(AopUtils.getAopProxy(this).getConfigByKey(configKey))
                .filter(StrUtil::isNotBlank)
                .map(Boolean::valueOf)
                .orElse(null);
    }

    @Override
    public LoginPageVO getLoginPageConfig() {
        var vo = new LoginPageVO();

        vo.setName(AopUtils.getAopProxy(this).getConfigByKey(ConfigConstants.SYSTEM_NAME));
        vo.setTitle(AopUtils.getAopProxy(this).getConfigByKey(ConfigConstants.SYSTEM_TITLE));
        vo.setDescription(AopUtils.getAopProxy(this).getConfigByKey(ConfigConstants.SYS_DESCRIPTION));
        vo.setLogo(AopUtils.getAopProxy(this).getConfigByKey(ConfigConstants.SYSTEM_LOGO));

        return vo;
    }

    @Override
    @CacheEvict(value = RedisConstants.CONFIG_KEY, key = "#entity.configKey")
    public boolean saveOrUpdate(SysConfig entity) {
        entity.setSystemConfig(null);
        this.checkConfigKey(entity);
        return super.saveOrUpdate(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<?> list) {
        for (Object object : list) {
            SysConfig config = this.getById(object.toString());
            AopUtils.getAopProxy(this).removeByConfig(config);
        }
        return true;
    }

    @CacheEvict(value = RedisConstants.CONFIG_KEY, key = "#config.configKey")
    public void removeByConfig(SysConfig config) {
        if (config == null) {
            return;
        }
        if (config.getSystemConfig()) {
            throw new BusinessException("系统配置[" + config.getConfigName() + "]不可删除");
        }
        super.removeById(config.getId());
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

}
