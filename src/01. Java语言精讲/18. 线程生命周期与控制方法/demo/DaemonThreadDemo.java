public class DaemonThreadDemo {
    public static void main(String[] args) {
        Thread daemon = new Thread(() -> {
            int count = 0;
            while (true) {
                System.out.println("守护线程运行中: " + count++);
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        });

        daemon.setDaemon(true); // 必须在 start() 之前设置
        daemon.start();

        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        System.out.println("主线程结束，JVM 将退出");
        // 守护线程被强制终止
    }
}