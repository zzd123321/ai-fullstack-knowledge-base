package demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureCreate {
    public static void main(String[] args) throws Exception {
        // 有返回值（使用 ForkJoinPool.commonPool()）
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1000); } catch (Exception e) {}
            return "Hello";
        });

        // 无返回值
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            System.out.println("异步任务执行");
        });

        // 指定线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "Result", executor);

        // 获取结果
        System.out.println(future1.get()); // Hello
        executor.shutdown();
    }
}