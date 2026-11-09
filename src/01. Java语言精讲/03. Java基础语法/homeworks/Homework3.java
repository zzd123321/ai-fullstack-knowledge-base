import java.util.Scanner;
import java.util.Random;

/**
 * 作业三：猜数字游戏
 * 程序随机生成 1~100 之间的整数，用户猜测，反馈大小，直到猜对，统计次数。
 */
public class Homework3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int target = random.nextInt(100) + 1; // 1~100
        int guessCount = 0;
        boolean guessed = false;

        System.out.println("猜数字游戏开始！数字范围 1~100");

        while (!guessed) {
            System.out.print("请输入你的猜测：");
            String input = scanner.nextLine();

            // 检查输入是否为整数
            int guess;
            try {
                guess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的整数！");
                continue;
            }

            // 检查范围
            if (guess < 1 || guess > 100) {
                System.out.println("数字必须在 1~100 之间，请重新输入");
                continue;
            }

            guessCount++;

            if (guess > target) {
                System.out.println("猜大了");
            } else if (guess < target) {
                System.out.println("猜小了");
            } else {
                guessed = true;
                System.out.println("恭喜你猜对了！答案是 " + target);
                System.out.println("你一共猜了 " + guessCount + " 次");
            }
        }

        scanner.close();
    }
}