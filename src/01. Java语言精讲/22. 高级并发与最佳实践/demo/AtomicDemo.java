package demo;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {

    // 原子整数，初始值为 0
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // 线程数量
        int threadCount = 10;
        // 每个线程执行累加次数
        int incrementsPerThread = 1000;

        // 创建并启动多个线程
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.incrementAndGet();  // 原子操作，线程安全
                }
            });
            threads[i].start();
        }

        // 等待所有线程执行完毕
        for (Thread t : threads) {
            t.join();
        }

        // 输出最终结果
        int expected = threadCount * incrementsPerThread;
        System.out.println("实际结果: " + counter.get());
        System.out.println("期望结果: " + expected);
        System.out.println("结果正确? " + (counter.get() == expected));
    }
}