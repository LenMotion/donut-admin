package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.entity.BaseCreatePo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "系统配置")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseCreatePo {

    /** 参数名称 */
    @Schema(description = "参数名称")
    @NotBlank(message = "参数名称不能为空")
    @Size(max = 100, message = "参数名称不能超过100个字符")
    private String configName;

    /** 参数键名 */
    @Schema(description = "参数键名")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

    /** 参数键值 */
    @Schema(description = "参数键值")
    @NotBlank(message = "参数键值不能为空")
    @Size(max = 500, message = "参数键值长度不能超过500个字符")
    private String configValue;

    /** 是否系统内置 */
    @Schema(description = "是否系统内置")
    private Boolean systemConfig;

    /** 备注 */
    @Schema(description = "备注")
    private String remark;

}
