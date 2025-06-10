package cn.lenmotion.donut.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author LenMotion
 */
@Data
@Schema(description = "更新状态请求")
public class BaseUpdateStatus {

    @Schema(name = "id", description = "主键")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(name = "status", description = "状态")
    @NotBlank(message = "状态不能为空")
    private String status;

}
