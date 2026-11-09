package homework1;

public class StateReader {
    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread tA = new Thread(() -> {});

        Thread tB = new Thread(() -> {
            while (true) {}
        });

        Thread tC = new Thread(() -> {
            try { Thread.sleep(10000); } catch (InterruptedException e) {}
        });

        Thread tD = new Thread(() -> {
            synchronized (lock) {
                try { lock.wait(); } catch (InterruptedException e) {}
            }
        });

        Thread tE = new Thread(() -> {
            synchronized (lock) {
                System.out.println("E 获得锁");
            }
        });

        tA.start();
        tB.start();
        tC.start();
        tD.start();

        Thread.sleep(500);
        synchronized (lock) {
            tE.start();
            Thread.sleep(500);
            System.out.println("tA 状态: " + tA.getState());
            System.out.println("tB 状态: " + tB.getState());
            System.out.println("tC 状态: " + tC.getState());
            System.out.println("tD 状态: " + tD.getState());
            System.out.println("tE 状态: " + tE.getState());
        }
    }
}