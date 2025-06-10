package cn.lenmotion.donut.system.remote.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.lenmotion.donut.system.remote.SysNoticeRemoteService;
import cn.lenmotion.donut.system.websocket.NoticePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysNoticeRemoteServiceImpl implements SysNoticeRemoteService {
    private final NoticePublisher noticePublisher;

    @Override
    public void closeNoticeWebsocket(SaTokenInfo saTokenInfo) {
        if (saTokenInfo == null) {
            log.info("saTokenInfo is null ");
        }
        noticePublisher.publishClose(saTokenInfo);
    }

}
