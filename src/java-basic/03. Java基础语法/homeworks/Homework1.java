/**
 * 作业一：代码输出结果预测
 * 运行此程序，观察每行输出的结果，与你的预测对比。
 */
public class Homework1 {
    public static void main(String[] args) {
        // 变量定义
        int a = 10;
        double b = 3.5;
        String c = "Java";
        boolean d = true;
        String e = null;

        // 1. 数据类型与类型检查
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);
        // 基本类型不能使用 instanceof，下面这行会编译错误，故注释掉
        // System.out.println(a instanceof Object);

        // 2. 变量类型转换
        System.out.println((int) b);
        System.out.println((double) a);
        System.out.println(String.valueOf(a) + c);
        System.out.println(Boolean.valueOf("true"));
        System.out.println(Boolean.valueOf("false"));
        System.out.println(Boolean.valueOf("hello"));

        // 3. 算术运算符
        System.out.println(a + 5);
        System.out.println(a / 4);
        System.out.println(a / 4.0);
        System.out.println(a % 4);
        System.out.println(a * a);
        System.out.println(c + " " + c);

        // 4. 字符串格式化
        String name = "Alice";
        int age = 25;
        System.out.printf("姓名: %s, 年龄: %d\n", name, age);
        System.out.printf("明年 %d 岁\n", age + 1);
        System.out.printf("%d + %d = %d\n", a, 5, a + 5);

        // 5. 比较运算符与逻辑运算符
        System.out.println(a > 5);
        System.out.println(a == 10);
        System.out.println(5 < a && a < 20);
        System.out.println(c.equals("java"));
        System.out.println('A' < 'a');

        // 6. 逻辑运算符
        System.out.println(true && false);
        System.out.println(true || false);
        System.out.println(!d);
        // Java中逻辑运算符只能用于boolean，下面两行会编译错误
        // System.out.println(0 && 5);
        // System.out.println(0 || 5);

        // 7. 三元运算符
        int score = 85;
        String result = (score >= 60) ? "及格" : "不及格";
        System.out.println(result);
        String level = (score >= 90) ? "A" : ((score >= 80) ? "B" : "C");
        System.out.println(level);

        // 8. 赋值运算符
        int x = 10;
        x += 5;
        System.out.println(x);
        x -= 3;
        System.out.println(x);
        x *= 2;
        System.out.println(x);
        x /= 4;
        System.out.println(x);

        String s = "Hi";
        s += " Java";
        System.out.println(s);
        // 字符串不支持 * 运算符，下面这行会编译错误
        // s *= 2;
    }
}