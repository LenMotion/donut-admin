package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LenMotion
 * 定时任务
 */
@Schema(description = "定时任务")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_job")
public class SysJob extends BaseCreatePo {
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    @TableField(value = "`name`")
    @Schema(description = "任务名称")
    private String name;

    /**
     * 任务表达式
     */
    @NotBlank(message = "任务表达式不能为空")
    @TableField(value = "cron")
    @Schema(description = "任务表达式")
    private String cron;

    /**
     * 任务执行器的class
     */
    @NotBlank(message = "任务执行器的class不能为空")
    @TableField(value = "task_class")
    @Schema(description = "任务执行器的class")
    private String taskClass;

    /**
     * 执行参数
     */
    @TableField(value = "params")
    @Schema(description = "执行参数")
    private String params;

    /**
     * 任务状态
     */
    @TableField(value = "`status`")
    @Schema(description = "任务状态")
    private String status;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    @Schema(description = "是否删除")
    private Boolean deleted;
}