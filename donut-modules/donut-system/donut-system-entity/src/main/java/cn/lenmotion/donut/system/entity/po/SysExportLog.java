package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 * 导出记录
 */
@Schema(description = "导出记录")
@Data
@TableName(value = "sys_export_log")
@EqualsAndHashCode(callSuper = true)
public class SysExportLog extends BaseCreatePo {
    @TableField(value = "`tenant_id`")
    @Schema(description = "租户ID")
    private Long tenantId;

    @TableField(value = "`user_id`")
    @Schema(description = "用户id")
    private Long userId;

    @TableField(value = "`export_type`")
    @Schema(description = "到处类型")
    private String exportType;

    @TableField(value = "`name`")
    @Schema(description = "文件名称")
    private String name;

    @TableField(value = "`file_info_id`")
    @Schema(description = "所属文件id")
    private String fileInfoId;

    @TableField(value = "`url`")
    @Schema(description = "文件地址")
    @Trans(type = TransType.AUTO_TRANS, key = BaseConstants.STORAGE_NAMESPACE)
    private String url;

    @TableField(value = "`status`")
    @Schema(description = "状态")
    @Trans(type = TransType.DICTIONARY, key = "sys_export_exec_status")
    private String status;

    @TableField(value = "`error_msg`")
    @Schema(description = "错误日志")
    private String errorMsg;

    @TableField(value = "`exec_time`")
    @Schema(description = "执行时间")
    private Long execTime;

    @TableField(value = "`remark`")
    @Schema(description = "备注")
    private String remark;

    @TableField(value = "`deleted`")
    @Schema(description = "删除标志（0存在 1删除）")
    private Boolean deleted;

}
