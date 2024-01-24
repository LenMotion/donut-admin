package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import cn.lenmotion.donut.system.entity.po.SysDept;
import cn.lenmotion.donut.system.entity.po.SysMenu;
import cn.lenmotion.donut.system.entity.po.SysPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lenmotion
 */
@Data
@Schema(description = "用户信息")
@EqualsAndHashCode(callSuper = true)
public class UserInfoVO extends BaseCreatePo {

    @Schema(description = "用户编号")
    private String userCode;

    @Schema(description = "部门ID")
    @Trans(type = TransType.SIMPLE, fields = "deptName", target = SysDept.class)
    private Long deptId;

    @Schema(description = "部门ID")
    @Trans(type = TransType.SIMPLE, fields = "postName", target = SysPost.class)
    private Long postId;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "用户邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "用户性别")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_SEX)
    private String sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "用户头像", hidden = true)
    @JsonIgnore
    @Trans(type = TransType.AUTO_TRANS, key = BaseConstants.STORAGE_NAMESPACE, ref = "avatarUrl")
    private String avatar;

    @Schema(description = "头像")
    private String avatarUrl;

    @Schema(description = "民族")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_NATION)
    private String nation;

    @Schema(description = "文化程度")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_CULTURE_TYPE)
    private String cultureType;

    @Schema(description = "帐号状态")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    private String status;

    @Schema(description = "住址")
    private String address;

    @Schema(description = "最后登录IP")
    private String loginIp;

    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;

    @Schema(description = "快速导航地址")
    private List<String> quickNav;

    @Schema(description = "快速导航菜单")
    private List<SysMenu> quickNavMenus;

    @Schema(description = "角色集合")
    private List<String> roles;

    @Schema(description = "权限集合")
    private List<String> perms;

}
