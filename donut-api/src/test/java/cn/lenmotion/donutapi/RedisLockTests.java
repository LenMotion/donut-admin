package cn.lenmotion.donutapi;

import cn.lenmotion.donut.framework.redis.RedisDistributionLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lenmotion
 */
@Slf4j
public class RedisLockTests extends DonutApiApplicationTests {

    @Autowired
    private RedisDistributionLock redisDistributionLock;

    @Test
    public void testRedisLock() throws Exception {
        Exec exec1 = new Exec(redisDistributionLock,"test");
        Exec exec2 = new Exec(redisDistributionLock,"test");
        Exec exec3 = new Exec(redisDistributionLock,"test");
        Exec exec4 = new Exec(redisDistributionLock,"test");

        exec1.start();
        exec2.start();
        exec3.start();
        exec4.start();

        Thread.sleep(20000);
        log.info("finish");
    }

    @Slf4j
    static class Exec extends Thread {

        private final RedisDistributionLock redisDistributionLock;
        private final String key;
        private String name;

        public Exec(RedisDistributionLock redisDistributionLock, String key) {
            this.redisDistributionLock = redisDistributionLock;
            this.key = key;
        }

        @Override
        public void run() {
            this.name = Thread.currentThread().getName();
            log.info("{} try lock", name);
            redisDistributionLock.tryLock(() -> {
                try {
                    log.info("{} lock success", name);
                    Thread.sleep(4000);
                    log.info("{} exec success", name);
                }catch (Exception e) {
                    log.error(name + " lock error", e);
                }
            }, key, 2000, 5);
            log.info("{} exec finished", name);
        }
    }

}
