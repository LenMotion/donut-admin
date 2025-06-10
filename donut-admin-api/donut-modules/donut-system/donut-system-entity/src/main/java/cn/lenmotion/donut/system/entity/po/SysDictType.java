package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

/**
 * @author lenmotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据字典类型")
public class SysDictType extends BaseCreatePo {

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典Key
     */
    @Schema(description = "字典Key")
    @NotBlank(message = "字典Key不能为空")
    private String dictKey;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    private String status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
