package demo.utils;

import java.util.*;

public class CollectionsDemo {
    public static void main(String[] args) {
        // ========== 排序 ==========
        List<Integer> numbers = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5));
        Collections.sort(numbers);  // 升序
        System.out.println(numbers);  // [1, 1, 3, 4, 5]
        Collections.sort(numbers, Collections.reverseOrder());  // 降序

        // ========== 查找 ==========
        int index = Collections.binarySearch(numbers, 3);  // 需先排序
        System.out.println("位置: " + index);

        // ========== 不可变集合 ==========
        List<String> unmodifiable = Collections.unmodifiableList(
                new ArrayList<>(Arrays.asList("a", "b", "c"))
        );
        // unmodifiable.add("d");  // UnsupportedOperationException!

        // ========== 同步集合（线程安全包装） ==========
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<>());

        // ========== 初始化 ==========
        List<Integer> emptyList = Collections.emptyList();  // 空列表
        Set<String> singleton = Collections.singleton("唯一");  // 单元素集合

        // ========== 填充和复制 ==========
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Collections.fill(list, "x");  // [x, x, x]
        Collections.copy(new ArrayList<>(list), list);  // 复制

        // ========== 最大/最小 ==========
        System.out.println(Collections.max(numbers));  // 最大值
        System.out.println(Collections.min(numbers));  // 最小值

        // ========== 频率统计 ==========
        System.out.println(Collections.frequency(numbers, 1));  // 出现次数

        // ========== 旋转 ==========
        Collections.rotate(numbers, 2);  // 循环右移2位
    }
}