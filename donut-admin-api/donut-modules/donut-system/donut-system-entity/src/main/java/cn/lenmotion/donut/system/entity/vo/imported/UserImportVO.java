package cn.lenmotion.donut.system.entity.vo.imported;

import cn.lenmotion.donut.core.constants.DictKeyConstants;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.core.trans.anno.UnTrans;
import org.dromara.core.trans.constant.TransType;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

/**
 * @author lenmotion
 */
@Data
@Validated
public class UserImportVO {

    @ExcelProperty(value = "用户编号")
    @NotBlank(message = "用户编号不能为空")
    private String userCode;

    @ExcelProperty(value = "用户账号")
    @NotBlank(message = "用户账号不能为空")
    private String username;

    @ExcelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    // 这个地方有注意事项: 只能选择String作为类型, 否则会报错,这是easyTrans的问题,等待修复后处理
    // String转成Long可以通过mapStruct转换的时候做处理
    @ExcelIgnore
    @UnTrans(type = TransType.SIMPLE, tableName = "sys_dept", columns = "dept_name", refs = "deptName")
    @NotBlank(message = "部门名称不能为空")
    private String deptId;

    @ExcelProperty(value = "部门名称")
    @ColumnWidth(21)
    private String deptName;

    @ExcelIgnore
    @UnTrans(type = TransType.SIMPLE, tableName = "sys_post", columns = "post_name", refs = "postName")
    @NotBlank(message = "岗位名称不能为空")
    private String postId;

    @ExcelProperty(value = "岗位名称")
    private String postName;

    @ExcelProperty(value = "邮箱")
    @ColumnWidth(25)
    private String email;

    @ExcelProperty(value = "手机号码")
    @ColumnWidth(18)
    private String phoneNumber;

    @ExcelIgnore
    @UnTrans(type = TransType.DICTIONARY, dict = DictKeyConstants.SYS_BASE_SEX, refs = "sexName")
    @NotBlank(message = "用户性别不能为空")
    private String sex;

    @ExcelProperty(value = "性别")
    private String sexName;

    @ExcelProperty(value = "生日")
    @NotNull(message = "生日不能为空")
    private LocalDate birthday;

    @ExcelIgnore
    @UnTrans(type = TransType.DICTIONARY, dict = DictKeyConstants.SYS_BASE_NATION, refs = "nationName")
    private String nation;

    @ExcelProperty(value = "民族")
    private String nationName;

    @ExcelIgnore
    @UnTrans(type = TransType.DICTIONARY, dict = DictKeyConstants.SYS_BASE_ID_TYPE, refs = "idTypeName")
    private String idType;

    @ExcelProperty(value = "证件类型")
    private String idTypeName;

    @ExcelProperty(value = "证件号码")
    private String idCard;

    @ExcelIgnore
    @UnTrans(type = TransType.DICTIONARY, dict = DictKeyConstants.SYS_BASE_CULTURE_TYPE, refs = "cultureTypeName")
    private String cultureType;

    @ExcelProperty(value = "文化程度")
    private String cultureTypeName;

    @ExcelIgnore
    @UnTrans(type = TransType.DICTIONARY, dict = DictKeyConstants.SYS_BASE_POLITICAL_OUTLOOK, refs = "politicalOutlookName")
    private String politicalOutlook;

    @ExcelProperty(value = "政治面貌")
    private String politicalOutlookName;

    @ExcelProperty(value = "住址")
    private String address;

    @ExcelProperty(value = "入职时间")
    @ColumnWidth(20)
    private LocalDate entryDate;

    @ExcelProperty(value = "备注")
    private String remark;

}
