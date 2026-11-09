package demo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    private static final int CAPACITY = 5;
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(CAPACITY);

    static class Producer implements Runnable {
        @Override
        public void run() {
            int value = 0;
            try {
                while (true) {
                    queue.put(value); // 如果队列满则阻塞
                    System.out.println("生产: " + value + "，队列大小: " + queue.size());
                    value++;
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {}
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    int value = queue.take(); // 如果队列空则阻塞
                    System.out.println(Thread.currentThread().getName() + " 消费: " + value + "，队列大小: " + queue.size());
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer(), "消费者1").start();
        new Thread(new Consumer(), "消费者2").start();
    }
}