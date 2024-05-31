package cn.lenmotion.donut.generator.entity.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
public class ColumnsQuery {

    @Schema(description = "数据源ID")
    private Long datasourceId;

    @Schema(description = "表名")
    private String tableName;

}
