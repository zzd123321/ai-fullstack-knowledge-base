package demo.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {
    private final Lock lock = new ReentrantLock();

    public void doWork() {
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁，执行任务");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " 未获得锁，执行其他操作");
        }
    }

    public static void main(String[] args) {
        TryLockDemo demo = new TryLockDemo();
        new Thread(demo::doWork, "线程A").start();
        new Thread(demo::doWork, "线程B").start();
    }
}