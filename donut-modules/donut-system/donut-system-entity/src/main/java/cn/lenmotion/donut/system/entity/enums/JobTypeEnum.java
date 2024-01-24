package cn.lenmotion.donut.system.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LenMotion
 */
@Getter
@AllArgsConstructor
public enum JobTypeEnum implements BaseEnum<String> {
    AUTO("0", "手动执行"),
    MANUAL("1", "自动执行"),
    ;

    private final String code;

    private final String remark;
}
