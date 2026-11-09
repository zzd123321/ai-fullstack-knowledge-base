package homework5;

public class BankTest {
    public static void main(String[] args) {
        // 显示银行信息（此时尚未创建账户）
        System.out.println("\n=== 银行初始信息 ===");
        System.out.println(Bank.getBankInfo());

        // 创建3个账户
        System.out.println("\n=== 创建账户 ===");
        Bank account1 = new Bank("张三", 1000);
        Bank account2 = new Bank("李四", 2000);
        Bank account3 = new Bank("王五", 500);

        // 显示银行信息
        System.out.println("\n=== 创建后银行信息 ===");
        System.out.println(Bank.getBankInfo());

        // 演示存取款
        System.out.println("\n=== 存取款操作 ===");
        account1.deposit(500);
        account1.withdraw(300);
        account1.withdraw(1500);  // 余额不足

        account2.deposit(-100);    // 非法存款
        account2.withdraw(500);

        account3.deposit(200);

        // 显示所有账户信息
        System.out.println("\n=== 所有账户信息 ===");
        account1.displayInfo();
        account2.displayInfo();
        account3.displayInfo();

        // 再次显示银行信息
        System.out.println("\n=== 最终银行信息 ===");
        System.out.println(Bank.getBankInfo());
//        System.out.println("总账户数（静态变量）: " + Bank.totalAccounts); // 注意：这里直接访问私有静态变量会报错，实际应通过getter
        // 使用getter访问
        System.out.println("总账户数（通过getter）: " + Bank.getTotalAccounts());
    }
}