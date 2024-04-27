package cn.lenmotion.donut.generator.entity.po;

import cn.lenmotion.donut.core.entity.BaseCreatePo;
import cn.lenmotion.donut.system.entity.po.SysMenu;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 * 代码生成table信息
 */
@Schema(description = "代码生成table信息")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "gen_table")
public class GenTable extends BaseCreatePo {
    /**
     * 表名
     */
    @TableField(value = "`table_name`")
    @Schema(description = "表名")
    private String tableName;

    /**
     * 数据源id
     */
    @TableField(value = "datasource_id")
    @Schema(description = "数据源id")
    private Long datasourceId;

    /**
     * 模块名称
     */
    @TableField(value = "module_name")
    @Schema(description = "模块名称")
    private String moduleName;

    /**
     * 所属模块
     */
    @TableField(value = "menu_id")
    @Schema(description = "所属模块")
    @Trans(type = TransType.SIMPLE, fields = "title", target = SysMenu.class)
    private Long menuId;

    /**
     * 功能名
     */
    @TableField(value = "feature_name")
    @Schema(description = "功能名")
    private String featureName;

    /**
     * 作者
     */
    @TableField(value = "author")
    @Schema(description = "作者")
    private String author;

    /**
     * 包名
     */
    @TableField(value = "package_name")
    @Schema(description = "包名")
    private String packageName;

    /**
     * 继承上级类
     */
    @TableField(value = "super_class")
    @Schema(description = "继承上级类")
    private String superClass;

    /**
     * 类名
     */
    @TableField(value = "class_name")
    @Schema(description = "类名")
    private String className;

    /**
     * 是否开启状态接口
     */
    @TableField(value = "status_api")
    @Schema(description = "是否开启状态接口")
    private Boolean statusApi;

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