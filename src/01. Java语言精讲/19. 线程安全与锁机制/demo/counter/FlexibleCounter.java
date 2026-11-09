package demo.counter;

public class FlexibleCounter {
    private int count = 0;
    private final Object lock = new Object();  // 专门作为锁的对象（推荐）

    public void increment() {
        synchronized (lock) {   // 使用指定的锁对象
            count++;
        }
    }

    public int getCount() {
        synchronized (lock) {
            return count;
        }
    }
}