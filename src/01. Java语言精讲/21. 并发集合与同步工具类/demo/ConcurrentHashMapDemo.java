package demo;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        // 原子操作
        map.computeIfAbsent("c", key -> 3); // c=3
        map.merge("d", 4, (oldVal, newVal) -> oldVal + newVal); // d=4
        map.merge("d", 5, (oldVal, newVal) -> oldVal + newVal); // d=9

        System.out.println(map); // {a=1, b=2, c=3, d=9}

        // 多线程并发 put
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                map.putIfAbsent("key", i); // 只插入一次
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终 key 的值: " + map.get("key")); // 首次插入的值
    }
}