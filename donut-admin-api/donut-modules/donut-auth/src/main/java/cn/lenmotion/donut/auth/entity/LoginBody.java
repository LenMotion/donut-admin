package cn.lenmotion.donut.auth.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录对象
 *
 * @author lenmotion
 */
@Data
@Schema(description = "用户登录对象")
public class LoginBody {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Schema(description = "用户密码")
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

    /**
     * 唯一标识
     */
    @Schema(description = "唯一标识")
    private String uuid = "";

}
