package cn.lenmotion.donut.core.context;

import cn.lenmotion.donut.core.annotation.DataScope;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author lenmotion
 */
public class DataScopeContext {

    private static final TransmittableThreadLocal<DataScope> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    public static void setDataScope(DataScope dataScope) {
        CONTEXT_HOLDER.set(dataScope);
    }

    public static DataScope getDataScope() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

}
