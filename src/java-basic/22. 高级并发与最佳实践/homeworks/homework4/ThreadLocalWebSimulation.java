package homeworks.homework4;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ThreadLocalWebSimulation {

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount);
        BusinessService businessService = new BusinessService();

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    // 1. 生成唯一请求 ID (UUID)
                    String requestId = UUID.randomUUID().toString();
                    // 2. 存入 ThreadLocal
                    RequestContext.setRequestId(requestId);

                    // 3. 执行业务方法（内部会获取 ThreadLocal 中的请求 ID）
                    businessService.process();

                } finally {
                    // 4. 清理 ThreadLocal，防止内存泄漏（重要）
                    RequestContext.clear();
                    // 通知主线程当前任务完成
                    latch.countDown();
                }
            }, "Request-Thread-" + i).start();
        }

        // 等待所有线程执行完毕
        latch.await();
        System.out.println("所有请求处理完成");
    }
}