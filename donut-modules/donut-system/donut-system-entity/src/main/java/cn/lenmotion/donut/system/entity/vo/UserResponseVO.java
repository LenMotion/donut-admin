package cn.lenmotion.donut.system.entity.vo;

import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.core.entity.BaseCreatePo;
import cn.lenmotion.donut.system.entity.po.SysRole;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LenMotion
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户信息")
public class UserResponseVO extends BaseCreatePo {

    @Schema(description = "用户编号")
    private String userCode;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "部门ID")
    @ExcelProperty
    private Long deptId;

    @Schema(description = "部门名称")
    @ColumnWidth(30)
    private String deptName;

    @Schema(description = "岗位ID")
    private Long postId;

    @Schema(description = "岗位名称")
    private String postName;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "手机号码")
    private String phoneNumber;

    @Schema(description = "用户性别")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_SEX)
    private String sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "头像")
    @Trans(type = TransType.AUTO_TRANS, key = BaseConstants.STORAGE_NAMESPACE, alias = "avatar")
    private String avatar;

    @Schema(description = "民族")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_NATION)
    private String nation;

    @Schema(description = "身份证类型")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_ID_TYPE)
    private String idType;

    @Schema(description = "身份证 - 做加密")
    private String idCard;

    @Schema(description = "文化程度")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_CULTURE_TYPE)
    private String cultureType;

    @Schema(description = "政治面貌")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_POLITICAL_OUTLOOK)
    private String politicalOutlook;

    @Schema(description = "住址")
    private String address;

    @Schema(description = "入职时间")
    private LocalDate entryDate;

    @Schema(description = "帐号状态（0正常 1停用）")
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS)
    private String status;

    @Schema(description = "最后登录IP")
    private String loginIp;

    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "角色id")
    @Trans(type = TransType.SIMPLE, fields = "roleName", target = SysRole.class)
    private String roleIdStr;

    @Schema(description = "角色id")
    @Trans(type = TransType.SIMPLE, fields = "roleName", target = SysRole.class)
    private List<Long> roleIds;

    @Schema(description = "用户关联部门")
    private List<UserDeptVO> userDeptList;

}
