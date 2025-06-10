package cn.lenmotion.donut.generator.entity.request;

import cn.lenmotion.donut.core.entity.BasePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据源请求对象")
public class GenDatasourceRequest extends BasePo {

    @Schema(description = "数据源类型 字典gen_datasource_type")
    private String type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "host")
    private String host;

    @Schema(description = "端口")
    private Integer port;

    @Schema(description = "库")
    private String schemaName;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "状态 字典sys_base_status")
    private Integer status;

}
