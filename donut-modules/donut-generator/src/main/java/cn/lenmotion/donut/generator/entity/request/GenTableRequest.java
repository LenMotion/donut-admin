package cn.lenmotion.donut.generator.entity.request;

import cn.lenmotion.donut.core.entity.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author lenmotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "表信息")
public class GenTableRequest extends BasePo {
    @Schema(description = "表名")
    private String tableName;

    @Schema(description = "模块名称")
    private String moduleName;

    @Schema(description = "所属模块")
    private Long menuId;

    @Schema(description = "功能名")
    private String featureName;

    @Schema(description = "作者")
    private String author;

    @Schema(description = "包名")
    private String packageName;

    @Schema(description = "继承上级类")
    private String superClass;

    @Schema(description = "类名")
    private String className;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否开启状态接口")
    private Boolean statusApi;

    @Schema(description = "字段信息")
    private List<GenTableColumnRequest> columns;

}
