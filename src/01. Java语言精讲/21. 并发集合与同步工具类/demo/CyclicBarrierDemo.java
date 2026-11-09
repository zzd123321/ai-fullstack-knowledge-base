package demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        int threadCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            // 所有线程到达屏障后执行的回调（可选项）
            System.out.println("所有线程到达屏障，执行回调");
        });

        for (int i = 0; i < threadCount; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.println("线程 " + id + " 到达屏障");
                    barrier.await(); // 等待其他线程
                    System.out.println("线程 " + id + " 越过屏障，继续执行");
                } catch (InterruptedException | BrokenBarrierException e) {}
            }).start();
        }
    }
}