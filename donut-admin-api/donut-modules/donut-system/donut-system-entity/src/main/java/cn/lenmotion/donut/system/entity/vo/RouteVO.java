package cn.lenmotion.donut.system.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "路由信息")
public class RouteVO {

    @Schema(description = "菜单名称 - 外部也要使用")
    private String title;

    @Schema(description = "路由名称")
    private String name;

    @Schema(description = "路由路径")
    private String path;

    @Schema(description = "组件")
    private String component;

    @Schema(description = "重定向")
    private String redirect;

    @Schema(description = "子路由")
    private List<RouteVO> children;

    @Schema(description = "路由元信息")
    private RouteMetaVO meta;

}
