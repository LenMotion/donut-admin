package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author LenMotion
 * 定时任务执行日志
 */
@Schema(description = "定时任务执行日志")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_job_log")
public class SysJobLog extends BaseCreatePo {
    /**
     * 任务id
     */
    @TableField(value = "job_id")
    @Schema(description = "任务id")
    private Long jobId;

    /**
     * 0自动执行 1手动执行
     */
    @TableField(value = "`type`")
    @Schema(description = "0自动执行 1手动执行")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_JOB_EXEC_TYPE)
    private String type;

    /**
     * 0执行中 1执行成功，2执行失败
     */
    @TableField(value = "`status`")
    @Schema(description = "0执行中 1执行成功，2执行失败")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_JOB_EXEC_STATUS)
    private String status;

    /**
     * 耗时(毫秒)
     */
    @TableField(value = "`time`")
    @Schema(description = "耗时(毫秒)")
    private Long time;

    /**
     * 错误日志
     */
    @TableField(value = "error_msg")
    @Schema(description = "错误日志")
    private String errorMsg;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    @Schema(description = "是否删除")
    private Boolean deleted;
}