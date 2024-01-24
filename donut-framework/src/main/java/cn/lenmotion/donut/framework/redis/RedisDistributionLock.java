package cn.lenmotion.donut.framework.redis;

import cn.hutool.cron.task.Task;

/**
 * @author lenmotion
 */
public interface RedisDistributionLock {

    /**
     * 执行的
     *
     * @param task 任务
     * @param key  加锁的key
     */
    void tryLock(Task task, String key);

    /**
     * 自动加锁 与重试次数
     *
     * @param task       任务
     * @param key        加锁的key
     * @param leaseTime  锁定时间
     */
    void tryLock(Task task, String key, int leaseTime);

    /**
     * 自动加锁 与重试次数
     *
     * @param task       任务
     * @param key        加锁的key
     * @param leaseTime  锁定时间
     * @param retryCount 重试次数
     */
    void tryLock(Task task, String key, int leaseTime, int retryCount);

    /**
     * 自动加锁 与重试次数 重试时间 过期时间
     *
     * @param task       任务
     * @param key        加锁的key
     * @param leaseTime  锁定时间
     * @param retryCount 重试次数
     * @param delayTime  重试时间
     */
    void tryLock(Task task, String key, int leaseTime, int retryCount, int delayTime);

}
