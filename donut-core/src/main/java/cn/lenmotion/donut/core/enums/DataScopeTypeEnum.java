package cn.lenmotion.donut.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum DataScopeTypeEnum implements BaseEnum<String> {
    DEPT("sys_dept", "部门"),
    ROLE("sys_role", "角色"),
    MENU("sys_menu", "菜单"),
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
