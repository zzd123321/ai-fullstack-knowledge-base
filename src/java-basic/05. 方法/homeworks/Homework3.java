/**
 * 作业三
 * 演示方法重载和可变参数
 */
public class Homework3 {
    // 两数之和
    public static int sum(int a, int b) {
        return a + b;
    }

    // 三数之和
    public static int sum(int a, int b, int c) {
        return a + b + c;
    }

    // 任意多个整数之和（可变参数）
    public static int sum(int... nums) {
        int total = 0;
        for (int n : nums) {
            total += n;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println("sum(3, 5) = " + sum(3, 5));              // 8
        System.out.println("sum(1, 2, 4) = " + sum(1, 2, 4));        // 7
        System.out.println("sum(1, 2, 3, 4, 5) = " + sum(1, 2, 3, 4, 5)); // 15
        System.out.println("sum() = " + sum());                      // 0
    }
}