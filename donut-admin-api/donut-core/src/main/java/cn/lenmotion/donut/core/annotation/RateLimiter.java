package cn.lenmotion.donut.core.annotation;

import cn.lenmotion.donut.core.enums.LimitTypeEnum;

import java.lang.annotation.*;

/**
 * @author lenmotion
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流类型
     */
    LimitTypeEnum limitType() default LimitTypeEnum.DEFAULT;

    /**
     * 同一时间段内多少人可以访问
     */
    int count() default 5;

    /**
     * 限流时间,单位秒
     */
    int time() default 10;

    /**
     * 默认的提示
     */
    String message() default "服务器限流异常，请稍候再试";

}
