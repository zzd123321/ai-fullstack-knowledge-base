package homework5;

/**
 * 测试类
 */
public class Homework5 {
    public static void main(String[] args) {
        // 1. 创建账户
        System.out.println("=== 创建账户 ===");
        BankAccount account = new BankAccount(1000.0);

        System.out.println("\n=== 测试1：存款负数 ===");
        try {
            account.deposit(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("存款失败: " + e.getMessage());
        }

        System.out.println("\n=== 测试2：取款500 ===");
        try {
            account.withdraw(500);
        } catch (InsufficientBalanceException e) {
            System.out.println("取款失败: " + e.getMessage());
        }

        System.out.println("\n=== 测试3：取款600（余额不足）===");
        try {
            account.withdraw(600);
        } catch (InsufficientBalanceException e) {
            System.out.println("取款失败: " + e.getMessage());
        }

        System.out.println("\n=== 测试4：取款400（正常）===");
        try {
            account.withdraw(400);
        } catch (InsufficientBalanceException e) {
            System.out.println("取款失败: " + e.getMessage());
        }

        System.out.println("\n=== 最终余额 ===");
        System.out.println("账户余额: " + account.getBalance());

        System.out.println("\n=== 测试5：创建负数余额账户 ===");
        try {
            BankAccount badAccount = new BankAccount(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("创建账户失败: " + e.getMessage());
        }
    }
}