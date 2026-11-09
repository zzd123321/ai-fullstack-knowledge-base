package homework1;

public class OrderTest {
    public static void main(String[] args) {
        System.out.println("1. main 开始");

        Thread t = new Thread(() -> {
            System.out.println("2. 子线程开始");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("3. 子线程结束");
        });

        t.start();

        System.out.println("4. main 继续执行");
        System.out.println("5. main 结束");
    }
}