package cn.lenmotion.donut.system.websocket;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import cn.lenmotion.donut.core.entity.ResponseResult;
import cn.lenmotion.donut.core.enums.ResponseCodeEnum;
import cn.lenmotion.donut.system.entity.po.SysNotice;
import com.alibaba.fastjson.JSONObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lenmotion
 */
@Slf4j
@Service
@ServerEndpoint(value = "/ws/notice/{token}")
@Component
public class NoticeWebsocketEndpoint {
    private static final String HEARTBEAT = "heartbeat";

    private final static ConcurrentHashMap<String, ConcurrentHashMap<String, WebsocketBean>> WEB_SOCKET_MAP = new ConcurrentHashMap<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        var loginId = StpUtil.getLoginIdByToken(token);
        if (loginId == null) {
            var result = ResponseResult.unLogin();
            session.getBasicRemote().sendText(JSONObject.toJSONString(result));
            session.close();
        } else {
            this.session = session;
            // 创建在线用户对象信息
            var websocketBean = new WebsocketBean();
            websocketBean.setSession(session);
            websocketBean.setUserId(loginId.toString());
            websocketBean.setToken(token);
            // 获取对应用户有多少个session，如果没有责创建
            var websocketBeanMap = WEB_SOCKET_MAP.getOrDefault(loginId.toString(), new ConcurrentHashMap<>());
            // 添加信息
            websocketBeanMap.put(token, websocketBean);
            // 更新用户的在线token
            WEB_SOCKET_MAP.put(loginId.toString(), websocketBeanMap);
            log.info("新用户连接成功！userId： {}", loginId);
        }
    }

    @OnClose
    public void onClose(@PathParam("token") String token, Session session) {
        var loginId = StpUtil.getLoginIdByToken(token);
        if (loginId != null && WEB_SOCKET_MAP.containsKey(loginId.toString())) {
            ConcurrentHashMap<String, WebsocketBean> websocketBeanMap = WEB_SOCKET_MAP.get(loginId.toString());
            websocketBeanMap.remove(token);
            WEB_SOCKET_MAP.put(loginId.toString(), websocketBeanMap);
            log.info("用户：[{}] 断开连接", loginId);
        }
        try {
            session.close();
        } catch (IOException e) {
            log.error("关闭websocket连接异常！", e);
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam("token") String token) {
        var loginId = StpUtil.getLoginIdByToken(token);

        if (loginId != null && HEARTBEAT.equals(message)) {
            this.sendMessage(ResponseResult.custom(ResponseCodeEnum.HEARTBEAT), session);
        } else {
            log.info("接收用户[{}]消息： {}", loginId, message);
        }
    }

    public void closeByUser(String userId, String token) {
        if (!WEB_SOCKET_MAP.containsKey(userId)) {
            return;
        }

        Map<String, WebsocketBean> websocketBeanMap = WEB_SOCKET_MAP.get(userId);

        WebsocketBean websocketBean = websocketBeanMap.get(token);
        if (ObjUtil.isNotNull(websocketBean)) {
            try {
                websocketBean.getSession().close();
            } catch (IOException e) {
                log.error("关闭websocket连接异常！", e);
            }
        }
    }

    public void sendMessage(SysNotice notice, List<Long> userIds) {
        for (Long userId : userIds) {
            if (!WEB_SOCKET_MAP.containsKey(userId.toString())) {
                continue;
            }

            Map<String, WebsocketBean> websocketBeanMap = WEB_SOCKET_MAP.get(userId.toString());
            for (WebsocketBean websocketBean : websocketBeanMap.values()) {
                this.sendMessage(ResponseResult.success(notice), websocketBean.getSession());
            }
        }
    }

    /**
     * 发送消息时调用
     *
     * @param result 消息内容
     */
    private <T> void sendMessage(ResponseResult<T> result, Session session) {
        try {
            session.getBasicRemote().sendText(JSONObject.toJSONString(result));
        } catch (IOException e) {
            log.error("发送消息失败", e);
        }
    }

}
