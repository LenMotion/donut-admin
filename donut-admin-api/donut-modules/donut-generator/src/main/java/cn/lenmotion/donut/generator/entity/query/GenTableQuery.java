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
@Schema(description = "GenTable查询参数")
public class GenTableQuery extends BasePageQuery {

    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "功能名")
    private String featureName;

}
