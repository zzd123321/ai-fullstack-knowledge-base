package homeworks.homework1;

import java.util.concurrent.*;

public class ThreadPoolOrder {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行任务 " + taskId);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            });
        }
        executor.shutdown();
    }
}