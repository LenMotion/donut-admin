package cn.lenmotion.donut.system.service;

import cn.lenmotion.donut.core.service.DonutService;
import cn.lenmotion.donut.system.entity.po.SysConfig;
import cn.lenmotion.donut.system.entity.query.ConfigQuery;
import cn.lenmotion.donut.system.entity.vo.LoginPageVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author lenmotion
 */
public interface SysConfigService extends DonutService<SysConfig> {

    /**
     * 获取所有的系统配置
     * @return
     */
    JSONObject selectSystemConfigList();

    /**
     * 更新系统配置
     * @param data
     * @return
     */
    boolean updateSystemConfig(JSONObject data);

    /**
     * 查询参数配置列表
     *
     * @param query 分页信息
     * @return 参数配置集合
     */
    IPage<SysConfig> selectConfigList(ConfigQuery query);

    /**
     * 刷新缓存
     */
    void resetConfigCache();

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String getConfigByKey(String configKey);

    /**
     * 获取Int类型的配置
     * @param configKey
     * @return
     */
    Integer getConfigIntValue(String configKey);

    /**
     * 获取布尔类型的配置
     * @param configKey
     * @return
     */
    Boolean getConfigBoolValue(String configKey);

    /**
     * 获取登录页的配置信息
     * @return
     */
    LoginPageVO getLoginPageConfig();

}
