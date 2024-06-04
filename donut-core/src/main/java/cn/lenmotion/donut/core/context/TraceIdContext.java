package cn.lenmotion.donut.core.context;

/**
 * @author lenmotion
 * 请求追踪
 */
public class TraceIdContext {

    private static final ThreadLocal<String> TRACE_ID_CONTEXT = new ThreadLocal<>();

    public static String getTraceId() {
        return TRACE_ID_CONTEXT.get();
    }

    public static void setTraceId(String traceId) {
        TRACE_ID_CONTEXT.set(traceId);
    }

    public static void clear() {
        TRACE_ID_CONTEXT.remove();
    }

}
