package demo;

import java.util.concurrent.*;

public class CustomThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                           // 核心线程数
                5,                           // 最大线程数
                60,                          // 空闲存活时间
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), // 有界队列
                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
        );

        // 提交任务
        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行任务 " + taskId);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            });
        }

        executor.shutdown();
    }
}