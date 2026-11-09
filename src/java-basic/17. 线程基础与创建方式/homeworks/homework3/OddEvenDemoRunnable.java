package homework3;

public class OddEvenDemoRunnable {
    public static void main(String[] args) {
        Thread even = new Thread(new OddEvenRunnable(2, "偶数线程"));
        Thread odd = new Thread(new OddEvenRunnable(1, "奇数线程"));

        even.start();
        odd.start();
    }
}