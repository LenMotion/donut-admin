package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "字典数据")
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends BaseCreatePo {

    /**
     * 字典Key
     */
    @Schema(description = "字典Key")
    @NotBlank(message = "字典Key不能为空")
    private String dictKey;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    @NotBlank(message = "字典标签不能为空")
    private String dictLabel;

    /**
     * 字典键值
     */
    @Schema(description = "字典键值")
    @NotBlank(message = "字典键值不能为空")
    private String dictValue;

    /**
     * 字典排序
     */
    @Schema(description = "字典排序")
    @NotNull(message = "字典排序不能为空")
    private Long dictSort;

    /**
     * 表格字典样式
     */
    @Schema(description = "表格字典样式")
    private String listClass;

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
