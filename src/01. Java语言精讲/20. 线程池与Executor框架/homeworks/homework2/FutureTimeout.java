package homeworks.homework2;

import java.util.concurrent.*;

public class FutureTimeout {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            System.out.println("任务开始");
            Thread.sleep(5000);
            System.out.println("任务完成");
            return 100;
        });

        try {
            Integer result = future.get(2, TimeUnit.SECONDS);
            System.out.println("结果: " + result);
        } catch (TimeoutException e) {
            System.out.println("超时！");
            future.cancel(true);
        }
        executor.shutdown();
    }
}