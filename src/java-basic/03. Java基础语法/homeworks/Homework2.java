/**
 * 作业二：循环与判断代码输出预测
 * 运行此程序，观察每行输出的结果，与你的预测对比。
 */
public class Homework2 {
    public static void main(String[] args) {
        // 1. while 循环 + if-else
        int n = 1;
        int result = 0;
        while (n <= 5) {
            if (n % 2 == 0) {
                result += n;
            } else {
                result -= n;
            }
            n++;
        }
        System.out.println(result);

        // 2. continue 和 break
        int num = 1;
        while (num <= 10) {
            if (num == 3) {
                num++;
                continue;
            }
            if (num == 7) {
                break;
            }
            System.out.println(num);
            num++;
        }

        // 3. do-while 循环
        int i = 0;
        do {
            System.out.println(i);
            i++;
        } while (i < 3);

        // 4. 嵌套条件 + switch（使用传统switch，兼容所有Java版本）
        int x = 15;
        if (x < 10) {
            System.out.println("A");
        } else if (x < 20) {
            switch (x % 2) {
                case 0:
                    System.out.println("B");
                    break;
                default:
                    System.out.println("C");
                    break;
            }
        } else {
            System.out.println("D");
        }

        // 5. 综合练习（for 循环）
        int a = 0;
        for (int i2 = 1; i2 <= 5; i2++) {
            if (i2 == 3) {
                a += 10;
            } else if (i2 % 2 == 0) {
                a += i2 * 2;
            } else {
                a += i2;
            }
        }
        System.out.println(a);
    }
}