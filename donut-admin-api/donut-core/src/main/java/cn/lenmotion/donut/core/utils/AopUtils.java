package cn.lenmotion.donut.core.utils;

import org.springframework.aop.framework.AopContext;

/**
 * @author lenmotion
 */
public class AopUtils {

    /**
     * 获取aop代理对象
     *
     * @param ignoredInvoker
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T ignoredInvoker) {
        return (T) AopContext.currentProxy();
    }

}
