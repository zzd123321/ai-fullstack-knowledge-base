package demo;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        int taskCount = 3;
        CountDownLatch latch = new CountDownLatch(taskCount);

        for (int i = 0; i < taskCount; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.println("线程 " + id + " 开始工作");
                    Thread.sleep((long) (Math.random() * 1000));
                    System.out.println("线程 " + id + " 完成");
                } catch (InterruptedException e) {}
                latch.countDown();
            }).start();
        }

        System.out.println("主线程等待所有任务完成...");
        latch.await(); // 阻塞直到计数器归零
        System.out.println("所有任务完成，主线程继续");
    }
}