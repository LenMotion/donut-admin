package cn.lenmotion.donut.system.entity.request;

import cn.lenmotion.donut.core.entity.BasePo;
import cn.lenmotion.donut.system.entity.po.SysUserDept;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LenMotion
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户信息请求对象")
public class SysUserRequest extends BasePo {

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "岗位ID")
    private Long postId;

    @Schema(description = "用户编号")
    @NotBlank(message = "用户编号不能为空")
    private String userCode;

    @Schema(description = "用户账号")
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    @Schema(description = "用户昵称")
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
    private String sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "民族")
    private String nation;

    @Schema(description = "身份证类型")
    private String idType;

    @Schema(description = "身份证 - 做加密")
    private String idCard;

    @Schema(description = "政治面貌")
    private String politicalOutlook;

    @Schema(description = "文化程度")
    private String cultureType;

    @Schema(description = "住址")
    private String address;

    @Schema(description = "入职时间")
    private LocalDate entryDate;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "额外所属部门")
    private List<SysUserDept> userDeptList = new ArrayList<>();

    @Schema(description = "角色id")
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;

}
