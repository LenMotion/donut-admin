package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

/**
 * @author LenMotion
 * 岗位信息表
 */
@Schema(description = "岗位信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_post")
public class SysPost extends BaseCreatePo {

    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 岗位类型 0全局岗位 1部门岗位
     */
    @TableField(value = "post_type")
    @Schema(description = "岗位类型 0全局岗位 1部门岗位")
    private String postType;

    /**
     * 部门id
     */
    @TableField(value = "dept_id")
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 岗位编码
     */
    @TableField(value = "post_code")
    @Schema(description = "岗位编码")
    @NotBlank(message = "岗位编码不能为空")
    private String postCode;

    /**
     * 岗位名称
     */
    @TableField(value = "post_name")
    @Schema(description = "岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    private String postName;

    /**
     * 显示顺序
     */
    @TableField(value = "order_no")
    @Schema(description = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNo;

    /**
     * 状态（0正常 1停用）
     */
    @TableField(value = "`status`")
    @Schema(description = "状态（0正常 1停用）")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @Schema(description = "备注")
    private String remark;

}