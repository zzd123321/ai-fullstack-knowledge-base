package homeworks.homework3;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureParallelDemo {

    public static void main(String[] args) {
        // 模拟三个 API 任务，延迟不同
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "数据A";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "数据B";
        });

        CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
            sleep(1500);
            return "数据C";
        });

        // 等待所有任务完成，并收集结果
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(task1, task2, task3);

        // 在所有任务完成后，拼接结果
        CompletableFuture<String> resultFuture = allFutures.thenApply(v ->
                Stream.of(task1, task2, task3)
                        .map(CompletableFuture::join)   // 此时都已完成，join 不会阻塞
                        .collect(Collectors.joining(", ", "[", "]"))
        );

        // 设置总超时 3 秒
        try {
            String result = resultFuture.get(3, TimeUnit.SECONDS);
            System.out.println("汇总结果: " + result);
        } catch (java.util.concurrent.TimeoutException e) {
            System.out.println("超时");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}