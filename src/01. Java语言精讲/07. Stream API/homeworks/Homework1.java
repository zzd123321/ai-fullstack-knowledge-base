import java.util.*;
import java.util.stream.*;

public class Homework1 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 6, 3, 7, 4);
        List<Integer> result = numbers.stream()
                .filter(n -> n > 3)
                .map(n -> n * 2)
                .sorted()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(result);
    }
}