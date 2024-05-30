package cn.lenmotion.donut.system.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum ExportStatusEnum implements BaseEnum<String> {
    RUNNING("0", "执行中"),
    FINISHED("1", "执行成功"),
    FAILED("2", "执行失败"),
    ;

    private final String code;

    private final String remark;
}