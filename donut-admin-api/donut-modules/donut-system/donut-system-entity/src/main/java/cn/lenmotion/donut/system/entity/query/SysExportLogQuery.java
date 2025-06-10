package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "导出记录查询")
public class SysExportLogQuery extends BasePageQuery {

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "导出类型")
    private String exportType;

}
