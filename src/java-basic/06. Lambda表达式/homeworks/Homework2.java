import java.util.Comparator;
import java.util.function.*;

public class Homework2 {
    public static void main(String[] args) {
        // 示例1
        Predicate<Integer> p1 = n -> n > 0;
        
        // 示例2
        Consumer<String> c1 = s -> System.out.println(s);
        
        // 示例3
        Function<String, Integer> f1 = s -> s.length();
        
        // 示例4
        Supplier<String> s1 = () -> "Hello";
        
        // 示例5
        Comparator<Integer> comp = (a, b) -> a - b;
        
        // 示例6
        Runnable r = () -> System.out.println("Running");
    }
}