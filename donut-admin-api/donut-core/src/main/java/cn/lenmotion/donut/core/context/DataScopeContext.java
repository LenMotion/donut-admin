package cn.lenmotion.donut.core.context;

import cn.lenmotion.donut.core.annotation.DataScope;
import cn.lenmotion.donut.core.entity.LoginInfo;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author lenmotion
 */
public class DataScopeContext {

    private static final TransmittableThreadLocal<DataScope> CONTEXT_HOLDER = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<Long> USER_CONTEXT_HOLDER = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<LoginInfo> LOGIN_INFO_CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    public static void setDataScope(DataScope dataScope, Long userId, LoginInfo loginInfo) {
        CONTEXT_HOLDER.set(dataScope);
        USER_CONTEXT_HOLDER.set(userId);
        LOGIN_INFO_CONTEXT_HOLDER.set(loginInfo);
    }

    public static DataScope getDataScope() {
        return CONTEXT_HOLDER.get();
    }

    public static Long getUserId() {
        return USER_CONTEXT_HOLDER.get();
    }

    public static LoginInfo getLoginInfo() {
        return LOGIN_INFO_CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
        USER_CONTEXT_HOLDER.remove();
        LOGIN_INFO_CONTEXT_HOLDER.remove();
    }

}
