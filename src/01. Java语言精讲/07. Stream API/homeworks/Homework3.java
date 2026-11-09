import java.util.*;
import java.util.stream.Collectors;

public class Homework3 {
    // 1. 返回所有偶数
    public static List<Integer> filterEven(List<Integer> list) {
        return list.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
    }

    // 2. 转为大写
    public static List<String> mapToUpperCase(List<String> list) {
        return list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    // 3. 平方和
    public static int sumOfSquares(List<Integer> list) {
        return list.stream()
                .mapToInt(n -> n * n)
                .sum();
    }

    // 4. 去重升序
    public static List<Integer> getDistinctSorted(List<Integer> list) {
        return list.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        // 测试 filterEven
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("filterEven: " + filterEven(numbers));

        // 测试 mapToUpperCase
        List<String> words = Arrays.asList("hello", "world", "java");
        System.out.println("mapToUpperCase: " + mapToUpperCase(words));

        // 测试 sumOfSquares
        List<Integer> nums = Arrays.asList(1, 2, 3);
        System.out.println("sumOfSquares: " + sumOfSquares(nums)); // 1+4+9=14

        // 测试 getDistinctSorted
        List<Integer> withDup = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3);
        System.out.println("getDistinctSorted: " + getDistinctSorted(withDup));
    }
}