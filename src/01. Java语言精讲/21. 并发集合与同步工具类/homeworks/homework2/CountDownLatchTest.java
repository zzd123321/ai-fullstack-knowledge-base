package homeworks.homework2;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        System.out.println("A: 主线程启动");

        new Thread(() -> {
            System.out.println("B: 线程1 开始");
            try { Thread.sleep(500); } catch (Exception e) {}
            System.out.println("C: 线程1 完成");
            latch.countDown();
        }).start();

        new Thread(() -> {
            System.out.println("D: 线程2 开始");
            try { Thread.sleep(300); } catch (Exception e) {}
            System.out.println("E: 线程2 完成");
            latch.countDown();
        }).start();

        latch.await();
        System.out.println("F: 主线程继续");
    }
}