package cn.lenmotion.donut.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 限制类型 默认不做其他策略的过滤，直接限制整个接口
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum LimitTypeEnum implements BaseEnum<String> {
    DEFAULT("0", "默认"),
    IP("1", "ip"),
    PARAMS("2", "参数"),
    TOKEN("3", "token"),
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
