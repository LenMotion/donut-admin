package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "菜单查询参数")
@EqualsAndHashCode(callSuper = true)
public class MenuQuery extends BaseQuery {
    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String title;

    /**
     * 页面名称 全局唯一
     */
    @Schema(description = "页面名称 全局唯一")
    private String name;
}
