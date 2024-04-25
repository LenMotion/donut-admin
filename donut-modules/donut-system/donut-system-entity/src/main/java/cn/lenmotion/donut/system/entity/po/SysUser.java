package cn.lenmotion.donut.system.entity.po;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author LenMotion
 */
@Data
@Schema(description = "用户信息")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseCreatePo {

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位ID")
    private Long postId;

    /**
     * 用户编号
     */
    @TableField(value = "user_code")
    @Schema(description = "用户编号")
    private String userCode;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @NotBlank(message = "用户账号不能为空")
    @Size(max = 30, message = "用户账号长度不能超过30个字符")
    private String username;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickName;

    /**
     * 真是姓名
     */
    @Schema(description = "真是姓名")
    @Size(max = 30, message = "用户真是姓名长度不能超过30个字符")
    private String realName;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @Size(max = 11, message = "手机号码长度不能超过11个字符")
    private String phoneNumber;

    /**
     * 用户性别
     */
    @Schema(description = "用户性别")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_SEX)
    private String sex;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @Schema(description = "生日")
    private LocalDate birthday;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 密码
     */
    @TableField(value = "password")
    @Schema(description = "密码", hidden = true)
    @JsonIgnore
    private String password;

    /**
     * 民族
     */
    @TableField(value = "nation")
    @Schema(description = "民族")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_NATION)
    private String nation;

    /**
     * 身份证类型
     */
    @TableField(value = "id_type")
    @Schema(description = "身份证类型")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_ID_TYPE)
    private String idType;

    /**
     * 身份证 - 做加密
     */
    @TableField(value = "id_card")
    @Schema(description = "身份证 - 做加密")
    private String idCard;

    /**
     * 文化程度
     */
    @TableField(value = "culture_type")
    @Schema(description = "文化程度")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_CULTURE_TYPE)
    private String cultureType;

    /**
     * 政治面貌
     */
    @TableField(value = "political_outlook")
    @Schema(description = "政治面貌")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_POLITICAL_OUTLOOK)
    private String politicalOutlook;

    /**
     * 住址
     */
    @TableField(value = "address")
    @Schema(description = "住址")
    private String address;

    /**
     * 入职时间
     */
    @TableField(value = "entry_date")
    @Schema(description = "入职时间")
    private LocalDate entryDate;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Schema(description = "帐号状态")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    private String status;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;

    //快捷导航，首页的快捷菜单id
    @Schema(description = "快捷导航，首页的快捷菜单id")
    private String quickNav;

    @Schema(description = "备注")
    private String remark;

    /**
     * 删除标志（0存在 1删除）
     */
    @Schema(description = "删除标志", hidden = true)
    @JsonIgnore
    private Integer deleted;

}
