package demo.counter;

public class ReentrantDemo {
    public synchronized void methodA() {
        System.out.println("methodA 获得锁");
        methodB(); // 同一个线程可以再次获得锁
    }

    public synchronized void methodB() {
        System.out.println("methodB 获得锁");
    }

    public static void main(String[] args) {
        new ReentrantDemo().methodA();
    }
}