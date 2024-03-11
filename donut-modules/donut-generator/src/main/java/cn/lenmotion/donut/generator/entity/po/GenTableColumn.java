package cn.lenmotion.donut.generator.entity.po;

import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author lenmotion
 * 代码生成table列信息
 */
@Schema(description = "代码生成table列信息")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "gen_table_column")
public class GenTableColumn extends BaseCreatePo {
    /**
     * 归属表id
     */
    @TableField(value = "table_id")
    @Schema(description = "归属表id")
    private Long tableId;

    /**
     * 列名
     */
    @TableField(value = "`column_name`")
    @Schema(description = "列名")
    private String columnName;

    /**
     * 备注
     */
    @TableField(value = "column_remark")
    @Schema(description = "备注")
    private String columnRemark;

    /**
     * 类型
     */
    @TableField(value = "column_type")
    @Schema(description = "类型")
    private String columnType;

    /**
     * Java列名
     */
    @TableField(value = "field_name")
    @Schema(description = "Java列名")
    private String fieldName;

    /**
     * 首字母大写的Java列名
     */
    @TableField(value = "upper_field_name")
    @Schema(description = "首字母大写的Java列名")
    private String upperFieldName;

    /**
     * java类型的class路径
     */
    @TableField(value = "java_type_class")
    @Schema(description = "java类型的class路径")
    private String javaTypeClass;

    /**
     * java类型
     */
    @TableField(value = "java_type")
    @Schema(description = "java类型")
    private String javaType;

    /**
     * 是否主键
     */
    @TableField(value = "id_field")
    @Schema(description = "是否主键")
    private Boolean idField;

    /**
     * 是否查询列
     */
    @TableField(value = "search_field")
    @Schema(description = "是否查询列")
    private Boolean searchField;

    /**
     * 查询列类型
     */
    @TableField(value = "search_field_type")
    @Schema(description = "查询列类型")
    private String searchFieldType;

    /**
     * 是否编辑列
     */
    @TableField(value = "edit_field")
    @Schema(description = "是否编辑列")
    private Boolean editField;

    /**
     * 编辑列类型
     */
    @TableField(value = "edit_field_type")
    @Schema(description = "编辑列类型")
    private String editFieldType;

    /**
     * 是否列表列
     */
    @TableField(value = "table_field")
    @Schema(description = "是否列表列")
    private Boolean tableField;

    /**
     * 是否忽略
     */
    @TableField(value = "ignore_field")
    @Schema(description = "是否忽略")
    private Boolean ignoreField;

    /**
     * 序号
     */
    @TableField(value = "sort_index")
    @Schema(description = "序号")
    private Integer sortIndex;

    /**
     * 字典key
     */
    @TableField(value = "dict_key")
    @Schema(description = "字典key")
    private String dictKey;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @Schema(description = "备注")
    private String remark;

    /**
     * 删除标志（0存在 1删除）
     */
    @TableField(value = "deleted")
    @Schema(description = "删除标志（0存在 1删除）")
    private Boolean deleted;
}