package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 菜单权限表
 * @author lenmotion
 */
@Schema(description = "菜单权限表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu")
public class SysMenu extends BaseCreatePo {

    /**
     * 菜单名称
     */
    @TableField(value = "title")
    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String title;

    /**
     * 页面名称 全局唯一
     */
    @TableField(value = "`name`")
    @Schema(description = "页面名称 全局唯一")
    private String name;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    @Schema(description = "父菜单ID")
    private Long parentId;

    /**
     * 菜单类型（0目录 1菜单 2按钮）
     */
    @TableField(value = "menu_type")
    @Schema(description = "菜单类型（0目录 1菜单 2按钮）")
    @NotBlank(message = "菜单类型不能为空")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_MENU_TYPE)
    private String menuType;

    /**
     * 路由地址
     */
    @TableField(value = "`path`", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "路由地址")
    private String path;

    /**
     * 当前激活的菜单
     */
    @TableField(value = "`current_active_menu`", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    /**
     * 重定向
     */
    @TableField(value = "redirect", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "重定向")
    private String redirect;

    /**
     * 组件路径
     */
    @TableField(value = "component", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "组件路径")
    private String component;

    /**
     * 是否忽略缓存（0不忽略 1忽略）
     */
    @TableField(value = "ignore_keep_alive")
    @Schema(description = "是否忽略缓存（0不忽略 1忽略）")
    private Boolean ignoreKeepAlive;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @TableField(value = "hide_menu")
    @Schema(description = "菜单状态（0显示 1隐藏）")
    private Boolean hideMenu;

    /**
     * 隐藏所有子菜单
     */
    @TableField(value = "hide_children_in_menu")
    @Schema(description = "隐藏所有子菜单")
    private Boolean hideChildrenInMenu;

    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField(value = "`status`")
    @Schema(description = "菜单状态（0正常 1停用）")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    private String status;

    /**
     * 权限标识
     */
    @TableField(value = "perms", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField(value = "icon", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 显示顺序
     */
    @TableField(value = "order_no")
    @Schema(description = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNo;

    /**
     * 路由参数
     */
    @TableField(value = "query", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "路由参数")
    private String query;

    /**
     * 是否为外链（0否 1是）
     */
    @TableField(value = "frame")
    @Schema(description = "是否为外链（0否 1是）")
    private Boolean frame;

    /**
     * 备注
     */
    @TableField(value = "remark", updateStrategy = FieldStrategy.ALWAYS)
    @Schema(description = "备注")
    private String remark;

    /**
     * 是否删除
     */
    @TableField(value = "deleted")
    @Schema(description = "是否删除")
    private Integer deleted;

}