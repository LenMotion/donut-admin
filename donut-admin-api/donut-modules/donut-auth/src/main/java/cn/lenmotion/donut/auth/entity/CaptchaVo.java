package cn.lenmotion.donut.auth.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author LenMotion
 */
@Data
@Schema(description = "验证码响应类")
public class CaptchaVo {

    @Schema(description = "是否启用")
    private Boolean captchaOnOff;

    @Schema(description = "随机码")
    private String uuid;

    @Schema(description = "验证码base64")
    private String img;

}
