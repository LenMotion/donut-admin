package cn.lenmotion.donut.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum implements BaseEnum<String> {
    DEPT_AND_CHILDREN("1", "本部门及以下数据权限"),
    DEPT("2", "本部门数据权限"),
    SELF("3", "用户自己的数据"),
    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 备注
     */
    private final String remark;

}
