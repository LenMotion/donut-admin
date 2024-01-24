package cn.lenmotion.donut.framework.redis.impl;

import cn.hutool.cron.task.Task;
import cn.lenmotion.donut.core.utils.AssertUtils;
import cn.lenmotion.donut.framework.redis.RedisDistributionLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RedisDistributionLockImpl implements RedisDistributionLock {
    // 默认重试次数0，不重试
    private static final int RETRY_COUNT = 0;
    private static final int DELAY_TIME = 100;
    private static final int LEASE_TIME = 10000;

    private final RedissonClient redissonClient;

    @Override
    public void tryLock(Task task, String key) {
        this.tryLock(task, key, LEASE_TIME, RETRY_COUNT);
    }

    @Override
    public void tryLock(Task task, String key, int leaseTime) {
        this.tryLock(task, key, leaseTime, RETRY_COUNT);
    }

    @Override
    public void tryLock(Task task, String key, int leaseTime, int retryCount) {
        this.tryLock(task, key, leaseTime, retryCount, DELAY_TIME);
    }

    @Override
    public void tryLock(Task task, String key, int leaseTime, int retryCount, int delayTime) {
        AssertUtils.notNull(task, "任务为空，无法执行");
        AssertUtils.notBlank(key, "任务key为空，无法执行");
        AssertUtils.isTrue(retryCount >= 0, "重试次数必须大于等于0");
        AssertUtils.isTrue(delayTime > 0, "重试间隔必须大于0");

        String lockKey = "lock:" + key;
        RLock rLock = redissonClient.getLock(lockKey);
        String threadName = Thread.currentThread().getName();

        while (retryCount-- >= 0) {
            log.info("thread {} try get lock: {}", threadName, key);
            try {
                if (rLock.tryLock(delayTime, leaseTime, TimeUnit.MILLISECONDS)) {
                    log.info("thread: {} get lock success! {}", Thread.currentThread().getName(), key);
                    try {
                        task.execute();
                        log.info("thread: {} exec finished!", Thread.currentThread().getName());
                    } finally {
                        retryCount = -1;
                        rLock.unlock();
                    }
                }
            } catch (Exception e) {
                log.error("thread: " + threadName + " key: " + key + " get lock error : ", e);
            }
        }
    }

}
