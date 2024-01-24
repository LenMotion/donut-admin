package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BasePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LenMotion
 * 用户额外任职关联表
 */
@Schema(description = "用户额外任职关联表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_dept")
public class SysUserDept extends BasePo {
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @TableField(value = "dept_id")
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 岗位ID
     */
    @TableField(value = "post_id")
    @Schema(description = "岗位ID")
    private Long postId;

    /**
     * 默认部门
     */
    @TableField(value = "default_dept")
    @Schema(description = "默认部门")
    private Boolean defaultDept;

}