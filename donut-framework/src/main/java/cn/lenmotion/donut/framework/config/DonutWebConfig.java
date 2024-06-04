package cn.lenmotion.donut.framework.config;

import cn.lenmotion.donut.framework.interceptor.TenantInterceptor;
import cn.lenmotion.donut.framework.interceptor.TraceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lenmotion
 */
@Configuration
@RequiredArgsConstructor
public class DonutWebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;
    private final TraceInterceptor traceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor);
        registry.addInterceptor(traceInterceptor);
    }

}
