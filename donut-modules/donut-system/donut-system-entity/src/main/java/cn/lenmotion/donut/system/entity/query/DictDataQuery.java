package cn.lenmotion.donut.system.entity.query;

import cn.lenmotion.donut.core.entity.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字典数据查询")
public class DictDataQuery extends BasePageQuery {

    /**
     * 字典Key
     */
    @Schema(description = "字典Key")
    private String dictKey;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
    @Schema(description = "字典键值")
    private String dictValue;

}
