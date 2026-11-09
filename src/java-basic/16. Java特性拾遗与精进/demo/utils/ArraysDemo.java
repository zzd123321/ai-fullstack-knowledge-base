package demo.utils;

import java.util.Arrays;
import java.util.List;

public class ArraysDemo {
    public static void main(String[] args) {
        // ========== toString ==========
        int[] arr = {3, 1, 4, 1, 5};
        System.out.println(Arrays.toString(arr));  // [3, 1, 4, 1, 5]

        // ========== 排序 ==========
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));  // [1, 1, 3, 4, 5]

        // ========== 二分查找 ==========
        int index = Arrays.binarySearch(arr, 3);
        System.out.println("位置: " + index);

        // ========== 填充 ==========
        Arrays.fill(arr, 0);
        System.out.println(Arrays.toString(arr));  // [0, 0, 0, 0, 0]

        // ========== 复制 ==========
        int[] copy = Arrays.copyOf(arr, 10);  // 扩容复制
        int[] range = Arrays.copyOfRange(arr, 0, 3);  // 截取复制

        // ========== 比较（deepEquals处理多维数组） ==========
        int[][] matrix1 = {{1, 2}, {3, 4}};
        int[][] matrix2 = {{1, 2}, {3, 4}};
        System.out.println(Arrays.equals(matrix1, matrix2));        // false
        System.out.println(Arrays.deepEquals(matrix1, matrix2));    // true

        // ========== 转字符串（多维） ==========
        System.out.println(Arrays.deepToString(matrix1));  // [[1, 2], [3, 4]]

        // ========== 数组转List ==========
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // list.add(6);  // UnsupportedOperationException!

        // ========== 并行操作 ==========
        Arrays.parallelSort(arr);          // 并行排序
        Arrays.parallelSetAll(arr, i -> i * 2);  // 并行设置
    }
}