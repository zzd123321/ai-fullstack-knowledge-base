package homeworks.homework4;

/**
 * 请求上下文，使用 ThreadLocal 存储当前请求 ID
 */
class RequestContext {
    // 静态 ThreadLocal，每个线程持有自己的请求 ID 副本
    private static final ThreadLocal<String> requestIdHolder = new ThreadLocal<>();

    /**
     * 设置当前请求 ID
     */
    public static void setRequestId(String requestId) {
        requestIdHolder.set(requestId);
    }

    /**
     * 获取当前请求 ID
     */
    public static String getRequestId() {
        return requestIdHolder.get();
    }

    /**
     * 清理当前线程的请求 ID（防止内存泄漏）
     */
    public static void clear() {
        requestIdHolder.remove();
    }
}