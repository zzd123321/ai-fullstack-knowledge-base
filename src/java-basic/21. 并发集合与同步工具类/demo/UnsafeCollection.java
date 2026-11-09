package demo;

import java.util.ArrayList;
import java.util.List;

public class UnsafeCollection {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("列表大小: " + list.size()); // 可能小于 2000
    }
}