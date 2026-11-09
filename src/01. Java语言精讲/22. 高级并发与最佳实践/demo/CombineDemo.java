package demo;

import java.util.concurrent.CompletableFuture;

public class CombineDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

        // 组合两个结果
        CompletableFuture<Integer> combined = future1.thenCombine(future2, (a, b) -> a + b);
        System.out.println(combined.get()); // 30

        // 依赖关系：future3 依赖 future1 的结果
        CompletableFuture<Integer> future3 = future1.thenCompose(result ->
                CompletableFuture.supplyAsync(() -> result * 2)
        );
        System.out.println(future3.get()); // 20

        // 等待所有任务完成
        CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2);
        all.get(); // 阻塞直到全部完成
    }
}