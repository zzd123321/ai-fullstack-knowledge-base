package homeworks.homework4;

import java.util.concurrent.*;

public class CustomThreadPoolRejection {

    public static void main(String[] args) {
        // 核心线程数2，最大线程数4，空闲存活时间5秒，工作队列容量2
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                  // corePoolSize
                4,                  // maximumPoolSize
                5,                  // keepAliveTime
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),   // 有界队列，容量2
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略：由调用线程执行
        );

        // 提交10个任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " 执行任务 " + taskId);
                try {
                    TimeUnit.SECONDS.sleep(1); // 模拟任务耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 关闭线程池（不再接受新任务），等待已有任务完成
        executor.shutdown();
        try {
            // 等待所有任务完成（最多等待10秒）
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}