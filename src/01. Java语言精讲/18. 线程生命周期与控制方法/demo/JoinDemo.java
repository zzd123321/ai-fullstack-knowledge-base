public class JoinDemo {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            System.out.println("子线程开始工作...");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("子线程工作完成！");
        });

        t.start();
        System.out.println("主线程等待子线程完成...");
        t.join(); // 阻塞，直到 t 执行完毕
        System.out.println("主线程继续执行");

        // 带超时：最多等待3秒
        // t.join(3000);
    }
}