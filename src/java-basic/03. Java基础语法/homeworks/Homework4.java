import java.util.Scanner;

/**
 * 作业四：简易计算器（简化版）
 * 用户输入两个整数和一个运算符（+ - * /），计算并输出结果。
 * 只运行一次，不循环，包含除零检查。
 */
public class Homework4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("简易计算器（只计算一次）");

        // 输入第一个数
        System.out.print("请输入第一个整数：");
        int num1 = scanner.nextInt();

        // 输入运算符
        System.out.print("请输入运算符（+ - * /）：");
        String op = scanner.next();

        // 输入第二个数
        System.out.print("请输入第二个整数：");
        int num2 = scanner.nextInt();

        double result = 0;
        boolean valid = true;

        // 根据运算符计算
        switch (op) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    System.out.println("错误：除数不能为 0！");
                    valid = false;
                } else {
                    result = (double) num1 / num2;
                }
                break;
            default:
                System.out.println("错误：不支持的运算符，请使用 + - * /");
                valid = false;
                break;
        }

        if (valid) {
            System.out.printf("结果：%d %s %d = %.2f\n", num1, op, num2, result);
        }

        scanner.close();
    }
}