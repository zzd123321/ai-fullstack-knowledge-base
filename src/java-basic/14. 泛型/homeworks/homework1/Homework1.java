package homework1;

import java.util.*;

public class Homework1<T, U> {
    private T first;
    private U second;

    public Homework1(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public static void main(String[] args) {
        Homework1<String, Integer> p1 = new Homework1<>("age", 25);
        Homework1<Double, String> p2 = new Homework1<>(3.14, "pi");
        Homework1<Integer, List<Integer>> p3 = new Homework1<>(1, Arrays.asList(2, 3, 4));

        System.out.println(p1.getFirst().length());
        System.out.println(p1.getSecond() + 5);
        System.out.println(p2.getFirst() + 1.0);
        System.out.println(p3.getSecond().get(1));
    }
}