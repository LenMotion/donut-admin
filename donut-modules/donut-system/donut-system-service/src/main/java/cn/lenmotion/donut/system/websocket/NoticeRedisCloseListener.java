package cn.lenmotion.donut.system.websocket;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.system.entity.po.SysNotice;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author lenmotion
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NoticeRedisCloseListener implements MessageListener<SaTokenInfo> {
    private final RedissonClient redissonClient;
    private final NoticeWebsocketEndpoint noticeWebsocketEndpoint;

    @PostConstruct
    public void init() {
        RTopic topic = redissonClient.getTopic(BaseConstants.NOTICE_CLOSE_REDIS_TOPIC);
        topic.addListener(SaTokenInfo.class, this);
        log.info("close websocket订阅者已初始化");
    }

    @PreDestroy
    public void destroy() {
        RTopic topic = redissonClient.getTopic(BaseConstants.NOTICE_CLOSE_REDIS_TOPIC);
        topic.removeListener(this);
        log.info("close websocket订阅者已销毁");
    }

    @Override
    public void onMessage(CharSequence channel, SaTokenInfo msg) {
        noticeWebsocketEndpoint.closeByUser(msg.getLoginId().toString(), msg.getTokenValue());
    }

}
