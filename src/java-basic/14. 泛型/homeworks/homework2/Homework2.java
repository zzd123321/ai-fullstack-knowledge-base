package homework2;

import java.util.*;

public class Homework2 {
    // 返回两个参数中较大的一个
    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    // 将数组中元素添加到列表
    public static <T> void addToList(T[] array, List<T> list) {
        for (T item : array) {
            list.add(item);
        }
    }

    public static void main(String[] args) {
        System.out.println(max(10, 20));
        System.out.println(max("apple", "banana"));

        String[] words = {"Hello", "World"};
        List<String> list = new ArrayList<>();
        addToList(words, list);
        System.out.println(list.size());
        System.out.println(list.get(0));

        // 下面这行能编译吗？
        // addToList(words, new ArrayList<Integer>());
    }
}