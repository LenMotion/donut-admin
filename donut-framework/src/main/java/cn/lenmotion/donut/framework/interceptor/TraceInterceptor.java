package cn.lenmotion.donut.framework.interceptor;

import cn.hutool.core.util.RandomUtil;
import cn.lenmotion.donut.core.context.TraceIdContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author lenmotion
 */
@Component
public class TraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var traceId = RandomUtil.randomString(5);
        TraceIdContext.setTraceId(traceId);
        MDC.put("TraceID", traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear();
        TraceIdContext.clear();
    }

}
