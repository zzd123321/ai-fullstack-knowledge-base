public class BlockedDemo {
    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("获取到锁");
            }
        });

        t1.start();
        Thread.sleep(100); // 确保 t1 先获得锁
        t2.start();
        Thread.sleep(100);
        System.out.println(t2.getState()); // BLOCKED
    }
}