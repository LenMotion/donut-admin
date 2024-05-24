package cn.lenmotion.donut.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author lenmotion
 * 租户信息的上下文
 */
public class TenantContext {

    private static final TransmittableThreadLocal<Long> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    public static void setTenant(Long tenant) {
        CONTEXT_HOLDER.set(tenant);
    }

    public static Long getTenant() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
