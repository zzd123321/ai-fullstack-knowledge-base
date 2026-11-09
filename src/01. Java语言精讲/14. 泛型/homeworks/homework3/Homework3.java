package homework3;

import java.util.*;

public class Homework3 {
    public static void processNumbers(List<? extends Number> list) {
        // double sum = 0;
        // for (Number n : list) {
        //     sum += n.doubleValue();
        // }
        // System.out.println(sum);
    }

    public static void addInteger(List<? super Integer> list) {
        // list.add(100);
        // list.add(200);
    }

    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>();
        List<Double> doubles = new ArrayList<>();
        List<Number> numbers = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        // 1. 哪些可以作为 processNumbers 的参数？
        processNumbers(ints);     // A
        processNumbers(doubles);  // B
        processNumbers(numbers);  // C
        processNumbers(objects);  // D

        // 2. 哪些可以作为 addInteger 的参数？
        addInteger(ints);         // E
        addInteger(doubles);      // F
        addInteger(numbers);      // G
        addInteger(objects);      // H

        // 3. 下面这两行正确吗？
        List<? extends Number> list1 = new ArrayList<Integer>();
        // list1.add(10);          // I

        List<? super Integer> list2 = new ArrayList<Number>();
        // list2.add("hello");     // J
        // list2.add(10);          // K
    }
}