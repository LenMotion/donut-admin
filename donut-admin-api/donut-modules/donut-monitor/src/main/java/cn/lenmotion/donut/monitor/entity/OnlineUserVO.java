package cn.lenmotion.donut.monitor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LenMotion
 */
@Data
public class OnlineUserVO {

    private Long id;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "登录IP地址")
    private String ip;

    @Schema(description = "浏览器类型")
    private String browser;

    @Schema(description = "操作系统")
    private String os;

    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    @Schema(description = "过期时间")
    private Long timeout;

}
