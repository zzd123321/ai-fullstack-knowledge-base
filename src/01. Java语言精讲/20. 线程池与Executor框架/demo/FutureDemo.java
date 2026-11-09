package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 提交 Callable 任务
        Future<Integer> future = executor.submit(() -> {
            System.out.println("开始计算...");
            Thread.sleep(2000);
            return 100;
        });

        // 主线程可以同时做其他事情
        System.out.println("主线程继续执行...");

        // 获取结果（阻塞等待）
        System.out.println("结果: " + future.get());

        // 提交多个任务并收集结果
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int num = i;
            futures.add(executor.submit(() -> num * num));
        }
        for (Future<Integer> f : futures) {
            System.out.println(f.get());
        }

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