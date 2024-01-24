package cn.lenmotion.donut.system.websocket;

import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.system.entity.po.SysNotice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * @author lenmotion
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticePublisher {

    private final RedissonClient redissonClient;

    public void publish(SysNotice notice) {
        log.info("publish notice: {}", notice);
        RTopic topic = redissonClient.getTopic(BaseConstants.NOTICE_REDIS_TOPIC);
        topic.publish(notice);
    }

}
