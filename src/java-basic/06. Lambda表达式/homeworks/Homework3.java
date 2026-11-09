import java.util.function.Predicate;

public class Homework3 {
    public static void main(String[] args) {
        Predicate<Integer> isEven = n -> {
            System.out.println("isEven(" + n + ")");
            return n % 2 == 0;
        };

        Predicate<Integer> isPositive = n -> {
            System.out.println("isPositive(" + n + ")");
            return n > 0;
        };

        Predicate<Integer> isLessThan10 = n -> {
            System.out.println("isLessThan10(" + n + ")");
            return n < 10;
        };

        // 组合1：isEven AND isPositive
        Predicate<Integer> p1 = isEven.and(isPositive);
        System.out.println("--- p1.test(8) ---");
        boolean r1 = p1.test(8);
        System.out.println("结果: " + r1);

        System.out.println("--- p1.test(-4) ---");
        boolean r2 = p1.test(-4);
        System.out.println("结果: " + r2);

        // 组合2：isEven OR isLessThan10
        Predicate<Integer> p2 = isEven.or(isLessThan10);
        System.out.println("--- p2.test(3) ---");
        boolean r3 = p2.test(3);
        System.out.println("结果: " + r3);

        System.out.println("--- p2.test(12) ---");
        boolean r4 = p2.test(12);
        System.out.println("结果: " + r4);
    }
}