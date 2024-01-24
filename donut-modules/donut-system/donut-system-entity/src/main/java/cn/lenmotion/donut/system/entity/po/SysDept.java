package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表
 *
 * @author LenMotion
 */
@Schema(description = "部门表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dept")
public class SysDept extends BaseCreatePo {

    /**
     * 父部门id
     */
    @TableField(value = "parent_id")
    @Schema(description = "父部门id")
    private Long parentId;

    /**
     * 等级
     */
    @TableField(value = "`level`")
    @Schema(description = "等级")
    private Integer level;

    /**
     * 祖级列表
     */
    @TableField(value = "ancestors")
    @Schema(description = "祖级列表")
    private String ancestors;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    @Schema(description = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    /**
     * 简称
     */
    @TableField(value = "short_name")
    @Schema(description = "简称")
    private String shortName;

    /**
     * 单位编号
     */
    @TableField(value = "dept_code")
    @Schema(description = "单位编号")
    private String deptCode;

    /**
     * 显示顺序
     */
    @TableField(value = "order_num")
    @Schema(description = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 负责人
     */
    @TableField(value = "leader")
    @Schema(description = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    @Schema(description = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @Schema(description = "邮箱")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @TableField(value = "`status`")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    @Schema(description = "部门状态（0正常 1停用）")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @Schema(description = "备注")
    private String remark;

    /**
     * 删除标志（0存在 1删除）
     */
    @TableField(value = "deleted")
    @Schema(description = "删除标志（0存在 1删除）")
    private Boolean deleted;
}