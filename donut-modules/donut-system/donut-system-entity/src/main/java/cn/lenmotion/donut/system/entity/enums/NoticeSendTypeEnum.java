package cn.lenmotion.donut.system.entity.enums;

import cn.lenmotion.donut.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lenmotion
 */
@Getter
@AllArgsConstructor
public enum NoticeSendTypeEnum implements BaseEnum<String> {
    ALL("0", "全图人员"),
    DEPT("1", "本部门人员"),
    DEPT_AND_CHILDREN("2", "本部门及以下人员"),
    USER("3", "指定人员"),
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

