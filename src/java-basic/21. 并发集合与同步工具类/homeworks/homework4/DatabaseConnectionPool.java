package homeworks.homework4;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class DatabaseConnectionPool {

    // 最大并发连接数为3
    private static final int MAX_CONNECTIONS = 3;
    private final Semaphore semaphore = new Semaphore(MAX_CONNECTIONS);

    /**
     * 模拟获取数据库连接
     */
    public void getConnection() throws InterruptedException {
        semaphore.acquire(); // 获取许可证，若没有则阻塞
        System.out.println(Thread.currentThread().getName() + " 获取连接，当前可用许可证：" + semaphore.availablePermits());
    }

    /**
     * 模拟释放数据库连接
     */
    public void releaseConnection() {
        semaphore.release();
        System.out.println(Thread.currentThread().getName() + " 释放连接，当前可用许可证：" + semaphore.availablePermits());
    }

    public static void main(String[] args) throws InterruptedException {
        DatabaseConnectionPool pool = new DatabaseConnectionPool();

        // 创建10个线程模拟客户端请求
        Thread[] clients = new Thread[10];
        for (int i = 0; i < 10; i++) {
            clients[i] = new Thread(() -> {
                try {
                    pool.getConnection();

                    // 模拟业务操作，随机休眠 500~2000ms
                    int sleepTime = 500 + (int) (Math.random() * 1500);
                    TimeUnit.MILLISECONDS.sleep(sleepTime);

                    pool.releaseConnection();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "客户端-" + i);
        }

        // 启动所有线程
        for (Thread t : clients) {
            t.start();
        }

        // 等待所有线程完成
        for (Thread t : clients) {
            t.join();
        }

        System.out.println("所有客户端执行完毕");
    }
}