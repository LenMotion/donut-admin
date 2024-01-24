package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BasePo;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "登录日志")
@EqualsAndHashCode(callSuper = true)
public class SysLoginLog extends BasePo {

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String username;

    /**
     * 登录状态 0成功 1失败
     */
    @Schema(description = "登录状态")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_LOGIN_STATUS)
    private String status;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    private String ip;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 提示消息
     */
    @Schema(description = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

}
