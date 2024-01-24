package cn.lenmotion.donut.system.websocket;

import jakarta.websocket.Session;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
public class WebsocketBean {

    // 授权token
    private String token;

    // 用户id
    private String userId;

    // 连接session对象
    private Session session;

}
