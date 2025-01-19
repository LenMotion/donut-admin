package cn.lenmotion.donut.system.entity.vo.export;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import cn.lenmotion.donut.system.entity.po.SysRole;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;
import org.dromara.core.trans.vo.VO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lenmotion
 */
@Data
public class UserExportVO implements VO {

    @ExcelIgnore
    @TableId
    private Long id;

    @ExcelProperty(value = "用户编号")
    private String userCode;

    @ExcelProperty(value = "用户账号")
    private String username;

    @ExcelProperty(value = "昵称")
    @ColumnWidth(15)
    private String nickName;

    @ExcelProperty(value = "部门名称")
    @ColumnWidth(21)
    private String deptName;

    @ExcelProperty(value = "岗位名称")
    @ColumnWidth(21)
    private String postName;

    @ExcelProperty(value = "邮箱")
    @ColumnWidth(25)
    private String email;

    @ExcelProperty(value = "手机号码")
    @ColumnWidth(18)
    private String phoneNumber;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, ref = "sexName", key = DictKeyConstants.SYS_BASE_SEX)
    private String sex;

    @ExcelProperty(value = "性别")
    private String sexName;

    @ExcelProperty(value = "生日")
    @ColumnWidth(10)
    private LocalDate birthday;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_NATION, ref = "nationName")
    private String nation;

    @ExcelProperty(value = "民族")
    private String nationName;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_ID_TYPE, ref = "idTypeName")
    private String idType;

    @ExcelProperty(value = "证件类型")
    @ColumnWidth(20)
    private String idTypeName;

    @ExcelProperty(value = "证件号码")
    private String idCard;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_CULTURE_TYPE, ref = "cultureTypeName")
    private String cultureType;

    @ExcelProperty(value = "文化程度")
    private String cultureTypeName;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_POLITICAL_OUTLOOK, ref = "politicalOutlookName")
    private String politicalOutlook;

    @ExcelProperty(value = "政治面貌")
    private String politicalOutlookName;

    @ExcelProperty(value = "住址")
    @ColumnWidth(30)
    private String address;

    @ExcelProperty(value = "入职时间")
    @ColumnWidth(20)
    private LocalDate entryDate;

    @ExcelIgnore
    @Trans(type = TransType.DICTIONARY, key = DictKeyConstants.SYS_BASE_STATUS, ref = "statusName")
    private String status;

    @ExcelProperty(value = "状态")
    private String statusName;

    @ExcelProperty(value = "最后登录IP")
    private String loginIp;

    @ExcelProperty(value = "最后登录时间")
    @ColumnWidth(25)
    private LocalDateTime loginDate;

    @ExcelProperty(value = "备注")
    private String remark;

    @ExcelIgnore
    @Trans(type = TransType.SIMPLE, fields = "roleName", target = SysRole.class, ref = "roleName")
    private String roleIdStr;

    @ExcelProperty(value = "角色")
    @ColumnWidth(30)
    private String roleName;

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(25)
    private LocalDateTime createTime;
    
}
