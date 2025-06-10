package cn.lenmotion.donut.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.lenmotion.donut.core.enums.ResponseCodeEnum;
import cn.lenmotion.donut.core.exception.BusinessException;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;

/**
 * @author lenmotion
 */
public class AssertUtils {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "object can not null");
    }

    public static void notNull(Object object, ResponseCodeEnum responseCodeEnum) {
        if (object == null) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BusinessException(message);
        }
    }

    public static void isNull(Object object, ResponseCodeEnum responseCodeEnum) {
        if (object != null) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void equals(Object obj1, Object obj2, String message) {
        if (ObjectUtils.notEqual(obj1, obj2)) {
            throw new BusinessException(message);
        }
    }

    public static void equals(Object obj1, Object obj2, ResponseCodeEnum responseCodeEnum) {
        if (ObjectUtils.notEqual(obj1, obj2)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEquals(Object obj1, Object obj2, ResponseCodeEnum responseCodeEnum) {
        if (!ObjectUtils.notEqual(obj1, obj2)) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notEquals(Object obj1, Object obj2, String message) {
        if (!ObjectUtils.notEqual(obj1, obj2)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollUtil.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "collection not empty");
    }

    public static void empty(Collection<?> collection) {
        empty(collection, "collection empty");
    }

    public static void empty(Collection<?> collection, String message) {
        if (CollUtil.isNotEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(Boolean bool, String message) {
        if (!bool) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(Boolean bool, ResponseCodeEnum responseCodeEnum) {
        if (!bool) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void isFalse(Boolean bool, String message) {
        if (bool) {
            throw new BusinessException(message);
        }
    }

    public static void isFalse(Boolean bool, ResponseCodeEnum responseCodeEnum) {
        if (bool) {
            throw new BusinessException(responseCodeEnum);
        }
    }

    public static void notBlank(String str, String message) {
        if (StrUtil.isBlank(str)) {
            throw new BusinessException(message);
        }
    }
}
