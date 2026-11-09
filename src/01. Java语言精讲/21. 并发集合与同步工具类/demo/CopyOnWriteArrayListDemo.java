package demo;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 迭代过程中允许修改（不会抛异常）
        for (String item : list) {
            System.out.println(item);
            if ("b".equals(item)) {
                list.add("d"); // 不会影响当前迭代
            }
        }
        System.out.println(list); // [a, b, c, d]
    }
}