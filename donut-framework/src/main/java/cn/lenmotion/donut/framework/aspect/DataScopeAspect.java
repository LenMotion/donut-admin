package cn.lenmotion.donut.framework.aspect;

import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.framework.config.MybatisPlusConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author lenmotion
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataScopeAspect {

    @Before(value = "@annotation(dataScope)")
    public void doBefore(JoinPoint joinPoint, DataScope dataScope) {
        MybatisPlusConfig.DonutDataPermissionHandler.DATA_SCOPE_THREAD_LOCAL.set(dataScope);
    }

    @After(value = "@annotation(dataScope)")
    public void doAfter(JoinPoint joinPoint, DataScope dataScope) {
        MybatisPlusConfig.DonutDataPermissionHandler.DATA_SCOPE_THREAD_LOCAL.remove();
    }

}
