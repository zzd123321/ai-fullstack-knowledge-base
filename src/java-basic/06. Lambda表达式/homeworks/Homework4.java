import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Homework4 {

    /**
     * 使用两个 Predicate 依次筛选列表（先 p1 再 p2），并用 Consumer 打印最终结果
     */
    public static void filterAndPrint(List<Integer> list,
                                      Predicate<Integer> p1,
                                      Predicate<Integer> p2,
                                      Consumer<Integer> consumer) {
        for (Integer num : list) {
            // 同时满足两个条件才放行（先 p1 再 p2 的效果等价于同时满足）
            if (p1.test(num) && p2.test(num)) {
                consumer.accept(num);
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.print("偶数且大于 5: ");
        filterAndPrint(numbers,
                n -> n % 2 == 0,   // 第一个条件：偶数
                n -> n > 5,        // 第二个条件：大于 5
                n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("奇数且小于 5: ");
        filterAndPrint(numbers,
                n -> n % 2 != 0,   // 第一个条件：奇数
                n -> n < 5,        // 第二个条件：小于 5
                n -> System.out.print(n + " "));
        System.out.println();
    }
}