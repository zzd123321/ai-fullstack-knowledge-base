package homeworks.homework1;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureOrder {
    public static void main(String[] args) throws Exception {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("A");
            return 1;
        }).thenApplyAsync(x -> {
            System.out.println("B");
            return x + 1;
        }).thenApplyAsync(x -> {
            System.out.println("C");
            return x * 2;
        }).thenAccept(x -> {
            System.out.println("结果: " + x);
        }).get();
    }
}