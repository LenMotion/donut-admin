package cn.lenmotion.donut.framework.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.lenmotion.donut.core.constants.BaseConstants;
import cn.lenmotion.donut.core.context.TenantContext;
import cn.lenmotion.donut.core.entity.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author lenmotion
 * 租户拦截器
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId = request.getHeader(BaseConstants.TENANT_HEADER);

        if (StrUtil.isNotBlank(tenantId)) {
            try {
                TenantContext.setTenant(Long.valueOf(tenantId));
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSONUtil.toJsonStr(ResponseResult.failed("租户信息错误")));
                return false;
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }

}
