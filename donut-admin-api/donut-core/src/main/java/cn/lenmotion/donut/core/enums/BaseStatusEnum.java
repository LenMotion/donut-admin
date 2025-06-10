package cn.lenmotion.donut.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态
 *
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum BaseStatusEnum implements BaseEnum<String> {
    // 用户状态
    ENABLED("0", "正常"),
    DISABLE("1", "停用");

    private final String code;
    private final String remark;

}
