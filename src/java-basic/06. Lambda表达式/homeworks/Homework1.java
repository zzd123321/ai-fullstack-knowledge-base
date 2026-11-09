import java.util.function.Predicate;
import java.util.Arrays;
import java.util.List;

public class Homework1 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3, 8, 12, 5, 7);
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isGreaterThan5 = n -> n > 5;

        System.out.println("Even numbers:");
        for (Integer n : numbers) {
            if (isEven.test(n)) {
                System.out.print(n + " ");
            }
        }
        System.out.println();

        System.out.println("Even and >5:");
        for (Integer n : numbers) {
            if (isEven.and(isGreaterThan5).test(n)) {
                System.out.print(n + " ");
            }
        }
    }
}