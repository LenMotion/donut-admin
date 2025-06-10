package cn.lenmotion.donut.system.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LenMotion
 */
@Getter
@AllArgsConstructor
public enum LoginStatusEnum implements BaseEnum<String> {
    SUCCESS("0", "成功"),
    FAILED("1", "失败"),
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
