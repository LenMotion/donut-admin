package cn.lenmotion.donut.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "路由元数据")
public class RouteMetaVO {

    @Schema(description = "路由title，一般必填")
    private String title;

    @Schema(description = "动态路由可打开Tab页数")
    private Integer dynamicLevel;

    @Schema(description = "动态路由的实际Path，即去除路由的动态部分")
    private String realPath;

    @Schema(description = "是否忽略权限，只在权限模式为Role的时候有效")
    private Boolean ignoreAuth;

    @Schema(description = "可以访问的角色，只在权限模式为Role的时候有效")
    private List<String> roles;

    @Schema(description = "是否忽略KeepAlive缓存")
    private Boolean ignoreKeepAlive;

    @Schema(description = "是否固定标签")
    private Boolean affix;

    @Schema(description = "图标，也是菜单图标")
    private String icon;

    @Schema(description = "内嵌iframe的地址")
    private String frameSrc;

    @Schema(description = "指定该路由切换的动画名")
    private String transitionName;

    @Schema(description = "隐藏该路由在面包屑上面的显示")
    private Boolean hideBreadcrumb;

    @Schema(description = "如果该路由会携带参数，且需要在tab页上面显示。则需要设置为true")
    private Boolean carryParam;

    @Schema(description = "隐藏所有子菜单")
    private Boolean hideChildrenInMenu;

    @Schema(description = "当前激活的菜单。用于配置详情页时左侧激活的菜单路径")
    private String currentActiveMenu;

    @Schema(description = "当前路由不再标签页显示")
    private Boolean hideTab;

    @Schema(description = "当前路由不再菜单显示")
    private Boolean hideMenu;

    @Schema(description = "菜单排序，只对第一级有效")
    private Integer orderNo;

    @Schema(description = "忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由")
    private Boolean ignoreRoute;

}
