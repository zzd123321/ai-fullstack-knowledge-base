public class StartVsRun {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("当前线程: " + Thread.currentThread().getName());
        });

        System.out.println("=== 调用 run() ===");
        t.run();  // 输出: 当前线程: main（在主线程中执行）

        System.out.println("=== 调用 start() ===");
        t.start(); // 输出: 当前线程: Thread-0（在新线程中执行）
    }
}