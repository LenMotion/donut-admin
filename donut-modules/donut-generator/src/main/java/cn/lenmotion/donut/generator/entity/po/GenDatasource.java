package cn.lenmotion.donut.generator.entity.po;

import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

/**
 * @author lenmotion
 * 数据源
 */
@Schema(description = "数据源")
@Data
@TableName(value = "gen_datasource")
@EqualsAndHashCode(callSuper = true)
public class GenDatasource extends BaseCreatePo {
    @TableField(value = "`type`")
    @Schema(description = "数据源类型 字典gen_datasource_type")
    @Trans(type = TransType.DICTIONARY, key = "gen_datasource_type")
    private String type;

    @TableField(value = "`name`")
    @Schema(description = "名称")
    private String name;

    @TableField(value = "`host`")
    @Schema(description = "host")
    private String host;

    @TableField(value = "`port`")
    @Schema(description = "端口")
    private Integer port;

    @TableField(value = "`schema_name`")
    @Schema(description = "库")
    private String schemaName;

    @TableField(value = "`username`")
    @Schema(description = "用户名")
    private String username;

    @TableField(value = "`password`")
    @Schema(description = "密码")
    private String password;

    @TableField(value = "`check_connection`")
    @Schema(description = "测试连接")
    private Boolean checkConnection;

    @TableField(value = "`status`")
    @Schema(description = "状态 字典sys_base_status")
    @Trans(type = TransType.DICTIONARY, key = "sys_base_status")
    private Integer status;

    @TableField(value = "`deleted`")
    @Schema(description = "是否删除")
    private Integer deleted;

}
