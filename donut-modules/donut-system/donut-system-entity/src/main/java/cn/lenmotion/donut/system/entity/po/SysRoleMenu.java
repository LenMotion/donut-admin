package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BasePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联表
 *
 * @author lenmotion
 */
@Schema(description = "角色和菜单关联表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role_menu")
public class SysRoleMenu extends BasePo {
    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    @Schema(description = "角色ID")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField(value = "menu_id")
    @Schema(description = "菜单ID")
    private Long menuId;

    /**
     * 是否是半菜单
     */
    @TableField(value = "half_menu")
    @Schema(description = "是否是半菜单")
    private Boolean halfMenu;
}