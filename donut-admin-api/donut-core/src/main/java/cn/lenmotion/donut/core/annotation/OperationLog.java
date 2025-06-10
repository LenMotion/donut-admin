package cn.lenmotion.donut.core.annotation;

import java.lang.annotation.*;

/**
 * @author lenmotion
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    String value() default "";

}
