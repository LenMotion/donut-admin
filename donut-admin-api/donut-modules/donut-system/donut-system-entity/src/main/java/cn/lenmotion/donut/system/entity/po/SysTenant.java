package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

/**
 * @author lenmotion
 * 租户表
 */
@Schema(description = "租户表")
@Data
@TableName(value = "sys_tenant")
@EqualsAndHashCode(callSuper = true)
public class SysTenant extends BaseCreatePo {
    @TableField(value = "`name`")
    @Schema(description = "租户名称")
    private String name;

    @TableField(value = "`description`")
    @Schema(description = "租户描述")
    private String description;

    @TableField(value = "`group_name`")
    @Schema(description = "所属集团")
    private String groupName;

    @TableField(value = "`status`")
    @Schema(description = "状态（0正常 1停用）")
    @Trans(type = TransType.DICTIONARY, key = "sys_base_status")
    private String status;

    @Schema(description = "管理员ID")
    private Long userId;

    @Schema(description = "角色ID")
    private Long roleId;

    @TableField(value = "`remark`")
    @Schema(description = "备注")
    private String remark;

    @TableField(value = "`deleted`")
    @Schema(description = "删除标志（0存在 1删除）")
    private Boolean deleted;

}
