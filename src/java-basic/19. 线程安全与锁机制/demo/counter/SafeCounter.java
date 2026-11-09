package demo.counter;

public class SafeCounter {
    private int count = 0;

    // 同步实例方法
    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}