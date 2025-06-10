package cn.lenmotion.donut.system.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements BaseEnum<String> {
    CATALOG("0", "目录", "LAYOUT"),
    MENU("1", "菜单", ""),
    BUTTON("2", "按钮", ""),
    ;

    /**
     * 编码
     */
    private final String code;
    /**
     * 备注
     */
    private final String remark;

    private final String component;
}
