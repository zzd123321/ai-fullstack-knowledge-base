package demo;

import java.util.concurrent.CompletableFuture;

public class ChainDemo {
    public static void main(String[] args) throws Exception {
        CompletableFuture.supplyAsync(() -> {
                    return "Hello";
                })
                .thenApply(s -> s + " World")          // 转换
                .thenApply(String::toUpperCase)         // 继续转换
                .thenAccept(System.out::println)        // 消费：输出 HELLO WORLD
                .thenRun(() -> System.out.println("完成")) // 执行动作
                .get();
    }
}