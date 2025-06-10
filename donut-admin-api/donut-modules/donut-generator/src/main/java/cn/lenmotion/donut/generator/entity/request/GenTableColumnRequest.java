package cn.lenmotion.donut.generator.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "表字段")
public class GenTableColumnRequest {

    @Schema(description = "归属表id")
    private Long tableId;

    @Schema(description = "列名")
    private String columnName;

    @Schema(description = "备注")
    private String columnRemark;

    @Schema(description = "类型")
    private String columnType;

    @Schema(description = "Java列名")
    private String fieldName;

    @Schema(description = "java类型的class路径")
    private String javaTypeClass;

    @Schema(description = "java类型")
    private String javaType;

    @Schema(description = "是否主键")
    private Boolean idField;

    @Schema(description = "是否查询列")
    private Boolean searchField;

    @Schema(description = "查询列类型")
    private String searchFieldType;

    @Schema(description = "是否编辑列")
    private Boolean editField;

    @Schema(description = "编辑列类型")
    private String editFieldType;

    @Schema(description = "是否列表列")
    private Boolean tableField;

    @Schema(description = "是否忽略")
    private Boolean ignoreField;

    @Schema(description = "序号")
    private Integer sortIndex;

    @Schema(description = "字典key")
    private String dictKey;

    @Schema(description = "备注")
    private String remark;

}
