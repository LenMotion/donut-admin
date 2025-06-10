package cn.lenmotion.donut.system.remote;

import cn.dev33.satoken.stp.SaTokenInfo;

/**
 * @author lenmotion
 */
public interface SysNoticeRemoteService {

    /**
     * 关闭通知的websocket
     * @param saTokenInfo
     */
    void closeNoticeWebsocket(SaTokenInfo saTokenInfo);

}
