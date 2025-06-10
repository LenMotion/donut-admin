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
@Schema(description = "配置查询")
public class ConfigQuery extends BasePageQuery {

    /** 参数名称 */
    @Schema(description = "参数名称")
    private String configName;

    /** 参数键名 */
    @Schema(description = "参数键名")
    private String configKey;

    /** 是否系统内置 */
    @Schema(description = "是否系统内置")
    private Boolean systemConfig;

}
