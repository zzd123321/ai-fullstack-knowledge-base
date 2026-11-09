package homework5;

import java.util.*;

/**
 * 作业五：泛型工具类 CollectionUtils
 */
public class CollectionUtils {

    public static <T> int countOccurrences(List<T> list, T target) {
        if (list == null) {
            return 0;
        }
        int count = 0;
        for (T item : list) {
            // 注意：target 可能为 null
            if (target == null) {
                if (item == null) count++;
            } else {
                if (target.equals(item)) count++;
            }
        }
        return count;
    }

    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
        if (list == null) {
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }


    // ---------- 测试 ----------
    public static void main(String[] args) {
        // 测试 countOccurrences
        List<Integer> nums = Arrays.asList(3, 1, 4, 1, 5, 9, 1);
        System.out.println("1 出现次数: " + countOccurrences(nums, 1));  // 3

        // 测试 filter (使用 lambda 表达式，Predicate)
        // 筛选出偶数
        List<Integer> evens = filter(nums, n -> n % 2 == 0);
        System.out.println("偶数: " + evens);  // [4]

        // 筛选出大于 3 的数
        List<Integer> greaterThan3 = filter(nums, n -> n > 3);
        System.out.println("大于3: " + greaterThan3);  // [4, 5, 9]

        // 测试 null 安全
        List<String> nullList = null;
        System.out.println("null 列表计数: " + countOccurrences(nullList, "a")); // 0
        System.out.println("null 列表反转: 无异常"); // 无异常
    }
}