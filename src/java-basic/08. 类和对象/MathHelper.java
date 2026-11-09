public class MathHelper {
    // 静态方法：可以直接通过类名调用
    public static int add(int a, int b) {
        return a + b;
    }

    // 静态方法中不能使用 this，只能访问静态成员
    public static void printInfo() {
        System.out.println("MathHelper 工具类");
        // System.out.println(this);  // 编译错误
    }

    public static void main(String[] args) {
        int sum = MathHelper.add(5, 3);  // 类名调用
        System.out.println(sum);
    }
}