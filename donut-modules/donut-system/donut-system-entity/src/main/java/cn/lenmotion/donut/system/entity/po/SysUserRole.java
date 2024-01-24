package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BasePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联表
 * @author lenmotion
 */
@Schema(description="用户和角色关联表")
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "sys_user_role")
public class SysUserRole extends BasePo {
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @Schema(description="用户ID")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    @Schema(description="角色ID")
    private Long roleId;
}