package homeworks.homework5;

import java.util.concurrent.locks.ReentrantLock;

public class BankAccountWithLock {
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccountWithLock(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        lock.lock(); // 直接获取锁
        try {
            if (amount > 0) {
                balance += amount;
                System.out.println(Thread.currentThread().getName() + " 存款 " + amount + "，当前余额：" + balance);
            }
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        // 使用 tryLock() 尝试获取锁，若获取失败则放弃操作
        if (!lock.tryLock()) {
            System.out.println(Thread.currentThread().getName() + " 获取锁失败，取款放弃");
            return;
        }
        try {
            if (amount > 0) {
                if (balance >= amount) {
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + " 取款 " + amount + "，当前余额：" + balance);
                } else {
                    System.out.println(Thread.currentThread().getName() + " 取款 " + amount + " 失败，余额不足！当前余额：" + balance);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccountWithLock account = new BankAccountWithLock(1000);
        int threadCount = 5;

        // 5个存款线程，每个存100（只执行一次）
        Thread[] depositThreads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            depositThreads[i] = new Thread(() -> {
                account.deposit(100);
            }, "存款线程-" + i);
        }

        // 5个取款线程，每个取150（只执行一次）
        Thread[] withdrawThreads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            withdrawThreads[i] = new Thread(() -> {
                account.withdraw(150);
            }, "取款线程-" + i);
        }

        // 启动所有线程
        for (Thread t : depositThreads) t.start();
        for (Thread t : withdrawThreads) t.start();

        // 等待所有线程结束
        for (Thread t : depositThreads) t.join();
        for (Thread t : withdrawThreads) t.join();

        System.out.println("最终余额：" + account.getBalance()); // 预期 750
    }
}