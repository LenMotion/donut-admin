package cn.lenmotion.donut.core.utils;

import cn.lenmotion.donut.core.enums.BaseEnum;

import java.util.EnumSet;
import java.util.Objects;

/**
 * @author lenmotion
 */
public class EnumUtils {

    /**
     * 根据code获取对应的枚举
     * @param clazz
     * @param code
     * @param <T>
     * @return
     */
    public static <C, T extends Enum<T> & BaseEnum<C>> T getByCode(Class<T> clazz, C code) {
        EnumSet<T> set = EnumSet.allOf(clazz);
        for (T t : set) {
            if (Objects.equals(t.getCode(), code)) {
                return t;
            }
        }
        return null;
    }

}
