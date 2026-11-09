import java.util.Arrays;

/**
 * 作业四：ArrayUtils 类
 * 包含静态方法操作数组，返回多值或统计信息
 */
public class ArrayUtils {
    // 返回数组中的最小值和最大值
    public static int[] minMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int min = arr[0];
        int max = arr[0];
        for (int v : arr) {
            if (v < min) min = v;
            if (v > max) max = v;
        }
        return new int[]{min, max};
    }

    // 计算平均值
    public static double average(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0.0;
        }
        int sum = 0;
        for (int v : arr) {
            sum += v;
        }
        return (double) sum / arr.length;
    }

    // 统计目标值出现次数
    public static int countOccurrences(int[] arr, int target) {
        int count = 0;
        for (int v : arr) {
            if (v == target) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int[] test = {3, 7, 1, 9, 2, 7, 7};
        System.out.println("数组: " + Arrays.toString(test));
        System.out.println("最小值和最大值: " + Arrays.toString(minMax(test)));
        System.out.println("平均值: " + average(test));
        System.out.println("数字 7 出现次数: " + countOccurrences(test, 7));
    }
}