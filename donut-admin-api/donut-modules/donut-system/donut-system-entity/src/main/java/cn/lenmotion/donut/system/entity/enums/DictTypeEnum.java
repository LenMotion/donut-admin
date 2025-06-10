package cn.lenmotion.donut.system.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum DictTypeEnum implements BaseEnum<String> {
    SYSTEM("0", "系统字典"),
    BUSINESS("1", "业务字典"),
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
