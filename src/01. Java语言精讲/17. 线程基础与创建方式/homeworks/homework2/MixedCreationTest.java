package homework2;

public class MixedCreationTest {
    public static void main(String[] args) {
        System.out.println("=== 程序开始 ===");

        // 方式1：继承 Thread
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("T1: 我是继承 Thread 方式");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1: 执行结束");
            }
        };

        // 方式2：实现 Runnable
        Runnable task = () -> {
            System.out.println("T2: 我是实现 Runnable 方式");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T2: 执行结束");
        };
        Thread t2 = new Thread(task);

        // 方式3：Lambda 直接创建
        Thread t3 = new Thread(() -> {
            System.out.println("T3: 我是 Lambda 方式");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T3: 执行结束");
        });

        // 启动线程
        t1.start();
        t2.start();
        t3.start();

        // 这里调用了 run() 而不是 start()
        t1.run();

        System.out.println("=== 主线程结束 ===");
    }
}