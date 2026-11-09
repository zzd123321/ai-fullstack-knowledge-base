import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 没有泛型时：使用 Object 存储
        List list = new ArrayList();
        list.add("hello");
        list.add(123);        // 可以存入任何类型

        String s1 = (String) list.get(0);  // 需要强制转换
        String s2 = (String) list.get(1); // 运行时 ClassCastException！


        // 使用泛型：指定元素类型
        List<String> list2 = new ArrayList<>();
        list2.add("hello");
//        list2.add(123);   // 编译错误！类型不匹配
        String s = list2.get(0);   // 无需强制转换



        List<String> names = Arrays.asList("Alice", "Bob");
        List<Integer> nums = Arrays.asList(1, 2, 3);
        printList(names); // 可以传入 String 列表
        printList(nums);  // 可以传入 Integer 列表



        List<Integer> ints = Arrays.asList(1, 2, 3);
        List<Double> doubles = Arrays.asList(1.5, 2.5);
        System.out.println(sumList(ints));    // 6.0
        System.out.println(sumList(doubles)); // 4.0


        List<Number> numbers = new ArrayList<>();
        addIntegers(numbers);  // 可以添加
        List<Object> objects = new ArrayList<>();
        addIntegers(objects);  // 也可以添加
//         List<Integer> ints = new ArrayList<>();
//         addIntegers(ints);  // 错误！Integer 不是 Integer 的父类（相等不算）

    }


    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    // 计算数字列表的总和
    public static double sumList(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void addIntegers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
        list.add(30);
    }

    public static void copy(List<? extends Number> src, List<? super Number> dest) {
        for (Number n : src) {
            dest.add(n);
        }
    }

}
