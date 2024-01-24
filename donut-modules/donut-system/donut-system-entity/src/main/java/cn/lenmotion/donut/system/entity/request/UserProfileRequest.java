package cn.lenmotion.donut.system.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "个人信息修改请求")
public class UserProfileRequest {

    @Schema(description = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    @Schema(description = "真实姓名")
    @Size(max = 30, message = "用户真实姓名长度不能超过30个字符")
    private String realName;

    @Schema(description = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    @Schema(description = "手机号码")
    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String phoneNumber;

    @Schema(description = "用户性别")
    @NotBlank(message = "用户性别不能为空")
    private String sex;

    @Schema(description = "年龄")
    @NotNull(message = "用户年龄不能为空")
    @Min(value = 0, message = "用户年龄不能小于0")
    @Max(value = 150, message = "用户年龄不能大于150")
    private Integer age;

    @Schema(description = "住址")
    private String address;

    @Schema(description = "文化程度")
    @NotBlank(message = "文化程度不能为空")
    private String cultureType;

    @Schema(description = "民族")
    @NotBlank(message = "用户民族不能为空")
    private String nation;

    @Schema(description = "快捷导航，首页的快捷菜单id")
    private List<String> quickNav;

}
