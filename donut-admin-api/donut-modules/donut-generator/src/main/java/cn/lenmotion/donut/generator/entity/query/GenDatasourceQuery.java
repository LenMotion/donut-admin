package cn.lenmotion.donut.generator.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "数据源查询")
public class GenDatasourceQuery extends BasePageQuery {

    @Schema(description = "数据源类型 字典gen_datasource_type")
    private String type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "host")
    private String host;

}
