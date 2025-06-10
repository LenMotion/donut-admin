package cn.lenmotion.donut.system.remote;

/**
 * @author lenmotion
 */
public interface SysConfigRemoteService {

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

}
