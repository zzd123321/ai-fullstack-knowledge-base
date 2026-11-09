package demo;

import java.util.concurrent.CompletableFuture;

public class ExceptionDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("出错了");
            return 100;
        }).exceptionally(ex -> {
            System.out.println("异常: " + ex.getMessage());
            return 0; // 备用值
        });

        System.out.println(future.get()); // 0

        // 使用 handle
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("错误");
            return "OK";
        }).handle((result, ex) -> {
            if (ex != null) {
                return "发生异常: " + ex.getMessage();
            }
            return result;
        });
        System.out.println(future2.get());
    }
}