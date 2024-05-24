package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BasePo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 操作日志记录
 * @author lenmotion
 */
@Schema(description = "操作日志记录")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_operation_log")
public class SysOperationLog extends BasePo {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 操作人员
     */
    @TableField(value = "user_id")
    @Schema(description = "操作人员")
    private Long userId;

    /**
     * 模块标题
     */
    @TableField(value = "title")
    @Schema(description = "模块标题")
    private String title;

    /**
     * 方法名称
     */
    @TableField(value = "`method`")
    @Schema(description = "方法名称")
    private String method;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    @Schema(description = "请求方式")
    private String requestMethod;

    /**
     * 请求URL
     */
    @TableField(value = "url")
    @Schema(description = "请求URL")
    private String url;

    /**
     * ip地址
     */
    @TableField(value = "ip")
    @Schema(description = "ip地址")
    private String ip;

    /**
     * 请求参数
     */
    @TableField(value = "params")
    @Schema(description = "请求参数")
    private String params;

    /**
     * 返回参数
     */
    @TableField(value = "json_result")
    @Schema(description = "返回参数")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @TableField(value = "`status`")
    @Schema(description = "操作状态（0正常 1异常）")
    private String status;

    /**
     * 错误消息
     */
    @TableField(value = "error_msg")
    @Schema(description = "错误消息")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "操作时间")
    private LocalDateTime createTime;
}