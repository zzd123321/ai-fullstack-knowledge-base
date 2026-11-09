package homework3;

public class OddEvenDemoLambda {
    public static void main(String[] args) {
        // 偶数线程
        Thread even = new Thread(() -> {
            for (int i = 2; i <= 20; i += 2) {
                System.out.println("偶数线程: " + i);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 奇数线程
        Thread odd = new Thread(() -> {
            for (int i = 1; i <= 19; i += 2) {
                System.out.println("奇数线程: " + i);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        even.start();
        odd.start();
    }
}