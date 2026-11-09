package demo.counter;

public class StaticCounter {
    private static int count = 0;

    public static synchronized void increment() { // synchronized
        count++; // 这不是原子操作！
    }

    public static synchronized int getCount() { // synchronized
        return count;
    }
}