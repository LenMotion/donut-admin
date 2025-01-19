package cn.lenmotion.donut.system.websocket;

import cn.hutool.core.collection.CollUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.context.TenantContext;
import cn.lenmotion.donut.system.entity.po.SysNotice;
import cn.lenmotion.donut.system.service.SysNoticeSendRelationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.trans.service.impl.TransService;
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
public class NoticeRedisMessageListener implements MessageListener<SysNotice> {

    private final NoticeWebsocketEndpoint noticeWebsocketEndpoint;
    private final RedissonClient redissonClient;
    private final SysNoticeSendRelationService noticeSendRelationService;
    private final TransService transService;

    @PostConstruct
    public void init() {
        RTopic topic = redissonClient.getTopic(BaseConstants.NOTICE_REDIS_TOPIC);
        topic.addListener(SysNotice.class, this);
        log.info("notice订阅者已初始化");
    }

    @PreDestroy
    public void destroy() {
        RTopic topic = redissonClient.getTopic(BaseConstants.NOTICE_REDIS_TOPIC);
        topic.removeListener(this);
        log.info("notice订阅者已销毁");
    }

    @Override
    public void onMessage(CharSequence channel, SysNotice notice) {
        log.info("Received message: channel = {}, msg = {}", channel, notice);

        TenantContext.setTenant(notice.getTenantId());
        try {
            transService.transOne(notice);

            var page = new Page<Long>(1, 500);
            // 发送消息
            this.sendNotice(page, notice);
        } finally {
            TenantContext.clear();
        }
    }

    // 发送通知
    private void sendNotice(IPage<Long> page, SysNotice notice) {
        // 获取当前页中对应通知的发送用户
        IPage<Long> userPage = noticeSendRelationService.getSendUserByNoticeId(page, notice.getId());
        // 如果没有用户接收，则直接返回
        if (CollUtil.isEmpty(userPage.getRecords())) {
            return;
        }
        // 通过WebsocketEndpoint发送消息
        noticeWebsocketEndpoint.sendMessage(notice, userPage.getRecords());
        // 如果当前页码大于等于用户页码，则递归发送下一页的通知
        if (page.getCurrent() >= userPage.getPages()) {
            page.setCurrent(page.getCurrent() + 1);
            this.sendNotice(page, notice);
        }
    }

}
