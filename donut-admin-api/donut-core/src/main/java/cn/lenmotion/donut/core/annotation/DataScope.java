package cn.lenmotion.donut.core.annotation;

import cn.lenmotion.donut.core.enums.DataScopeTypeEnum;

import java.lang.annotation.*;

/**
 * @author lenmotion
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    DataScopeTypeEnum type();

    String field() default "";

    String alias() default "";

    /**
     * 是否忽略租户管理员 true表示租户管理员不加过滤条件，false表示需要加过滤条件
     */
    boolean tenantAdminIgnore() default false;

}
