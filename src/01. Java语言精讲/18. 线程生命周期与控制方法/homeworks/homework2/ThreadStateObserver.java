package homework2;

public class ThreadStateObserver {
    public static void main(String[] args) throws InterruptedException {
        // 线程 A：计数 + 休眠
        Thread threadA = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("A: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            try {
                Thread.sleep(2000); // 额外等待
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 线程 B：等待 A 结束
        Thread threadB = new Thread(() -> {
            try {
                threadA.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Waiter: A finished");
        });

        // 线程 C：监控所有线程状态
        Thread threadC = new Thread(() -> {
            while (true) {
                Thread.State stateA = threadA.getState();
                Thread.State stateB = threadB.getState();
                Thread.State stateC = Thread.currentThread().getState();
                System.out.printf("Monitor: A=%s, B=%s, C=%s%n", stateA, stateB, stateC);
                if (stateA == Thread.State.TERMINATED && stateB == Thread.State.TERMINATED) {
                    System.out.println("Monitor: all done");
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // 启动线程
        threadA.start();
        threadB.start();
        threadC.start();

        // 主线程等待 A 和 B 结束
        threadA.join();
        threadB.join();

        System.out.println("Main: exit");
    }
}