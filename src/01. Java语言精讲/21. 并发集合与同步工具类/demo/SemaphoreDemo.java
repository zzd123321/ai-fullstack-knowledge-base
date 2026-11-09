package demo;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); // 最多允许3个线程同时访问

        for (int i = 0; i < 10; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    semaphore.acquire(); // 获取许可证
                    System.out.println("线程 " + id + " 获取许可证，开始工作");
                    Thread.sleep((long) (Math.random() * 2000));
                    System.out.println("线程 " + id + " 释放许可证");
                } catch (InterruptedException e) {}
                semaphore.release(); // 释放许可证
            }).start();
        }
    }
}