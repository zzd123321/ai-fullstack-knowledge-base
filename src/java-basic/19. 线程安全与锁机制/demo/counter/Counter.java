package demo.counter;

public class Counter {
    private int count = 0;

    public void increment() { // synchronized
        count++; // 这不是原子操作！
    }

    public int getCount() { // synchronized
        return count;
    }
}