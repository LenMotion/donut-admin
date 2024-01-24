package cn.lenmotion.donut.system.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author LenMotion
 */
@Data
@Schema(description = "个人头像修改请求")
public class UserAvatarRequest {

    @NotBlank(message = "头像不能为空")
    @Schema(description = "用户头像")
    private String avatar;

}
