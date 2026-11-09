package homeworks.homework2;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureException {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("错误!");
            return 10;
        }).exceptionally(ex -> {
            System.out.println("处理异常: " + ex.getMessage());
            return -1;
        }).thenApply(x -> x * 2);

        System.out.println(future.get());
    }
}