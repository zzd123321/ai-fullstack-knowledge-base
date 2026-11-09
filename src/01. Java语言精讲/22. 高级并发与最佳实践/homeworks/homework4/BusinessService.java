package homeworks.homework4;

class BusinessService {
    public void process() {
        // 模拟业务逻辑，打印当前线程名和请求 ID
        String requestId = RequestContext.getRequestId();
        System.out.printf("[%s] 处理请求，请求 ID: %s%n",
                Thread.currentThread().getName(), requestId);

        // 模拟耗时操作
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}