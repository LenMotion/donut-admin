package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典类型查询")
public class DictTypeQuery extends BasePageQuery {

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;

    /**
     * 字典Key
     */
    @Schema(description = "字典Key")
    private String dictKey;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

}
