package demo.asserts;

public class AssertDemo {
    public static int divide(int a, int b) {
        // 断言：除数不能为0
        assert b != 0 : "除数不能为0";
        return a / b;
    }

    public static int factorial(int n) {
        // 断言：n 不能为负数
        assert n >= 0 : "n必须是非负数";
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    public static void process(int value) {
        // 断言：value 在有效范围内
        assert value >= 0 && value <= 100 : "value必须在0~100之间";
        System.out.println("处理值: " + value);
    }

    public static void main(String[] args) {
        // 默认：断言被禁用，不会执行
        System.out.println(divide(10, 0));  // 算术异常

        // 启用断言的方式见下文
    }
}