package cn.lenmotion.donut.framework.redis;

import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lenmotion
 */
public interface RedisService {

    /**
     * 获取对应值的过期时间
     * @param key
     * @return
     */
    long ttl(String key);

    /**
     * zongshu
     * @return
     */
    int size();

    /**
     * all key
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);

    /**
     * 保存属性
     * @param key
     * @param value
     * @param time
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 获取属性
     * @param key
     * @return
     */
    Object getObj(String key);

    /**
     * 获取值
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 获取值
     * @param key
     * @param <T>
     * @return
     */
    <T> T getObject(String key);

    /**
     * 删除属性
     * @param key
     * @return
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     * @param keys
     * @return
     */
    Long del(String... keys);

    /**
     * 设置过期时间
     * @param key
     * @param time
     * @return
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     * @param key
     * @return
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     * @param key
     * @param delta
     * @return
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     * @param key
     * @param delta
     * @return
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     * @param key
     * @param hashKey
     * @return
     */
    Object hGet(String key, String hashKey);

    /**
     * 获取Hash结构中的属性
     * @param key
     * @param hashKey
     * @param clz
     * @param <T>
     * @return
     */
    <T> T hGet(String key, String hashKey, Class<T> clz);

    /**
     * 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
     * @param time
     * @return
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     * @param key
     * @return
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     * @param key
     * @param map
     * @param time
     * @return
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     * @param key
     * @param map
     */
    void hSetAll(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     * @param key
     * @param hashKey
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     * @param key
     * @param hashKey
     * @return
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     * @param key
     * @return
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     * @param key
     * @param values
     * @return
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     * @param key
     * @param time
     * @param values
     * @return
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     * @param key
     * @param value
     * @return
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     * @param key
     * @return
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     * @param key
     * @param values
     * @return
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     * @param key
     * @return
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     * @param key
     * @param index
     * @return
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     * @param key
     * @param value
     * @return
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     * @param key
     * @param value
     * @param time
     * @return
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     * @param key
     * @param values
     * @return
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     * @param key
     * @param time
     * @param values
     * @return
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     * @param key
     * @param count
     * @param value
     * @return
     */
    Long lRemove(String key, long count, Object value);

    /**
     * 获取旧的值设置新的值
     * @param key
     * @param value
     * @return
     */
    Object getAndSet(String key, Object value);

    /**
     * 设置一直不存在的值
     * @param key
     * @param value
     * @return
     */
    boolean setIfAbsent(String key, Object value);

    /**
     * 获取自增对象
     * @param key
     * @return
     */
    RedisAtomicLong getRedisAtomic(String key);

}
