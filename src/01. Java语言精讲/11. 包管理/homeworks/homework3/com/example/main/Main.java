package com.example.math;

/**
 * 主程序入口
 */
public class Main {

    public static void main(String[] args) {
        // 创建 Calculator 对象
        Calculator calc = new Calculator();

        // 调用 add 方法并打印结果
        int result = calc.add(3, 5);
        System.out.println("3 + 5 = " + result);

        // 测试其他数值
        System.out.println("10 + 20 = " + calc.add(10, 20));
        System.out.println("-5 + 8 = " + calc.add(-5, 8));
    }
}