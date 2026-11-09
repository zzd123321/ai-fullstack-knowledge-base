package homework5;

class Bank {
    // 静态变量
    private static String bankName;
    private static int totalAccounts = 0;
    private static int nextAccountNumber = 1001;  // 账号起始值

    // 实例变量
    private int accountNumber;
    private String owner;
    private double balance;

    // 静态代码块：初始化银行名称
    static {
        bankName = "中国银行";
        System.out.println("银行系统初始化完成，银行名称: " + bankName);
    }

    // 构造方法：创建账户时自动生成账号、递增总账户数
    public Bank(String owner, double initialBalance) {
        this.owner = owner;
        this.accountNumber = nextAccountNumber++;
        this.balance = initialBalance;
        totalAccounts++;
        System.out.println("创建账户成功，账号: " + accountNumber + ", 户主: " + owner);
    }

    // 存款
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("存入 " + amount + "，当前余额: " + balance);
        } else {
            System.out.println("存款金额必须大于0");
        }
    }

    // 取款
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("取出 " + amount + "，当前余额: " + balance);
        } else if (amount <= 0) {
            System.out.println("取款金额必须大于0");
        } else {
            System.out.println("余额不足！当前余额: " + balance);
        }
    }

    // 查询余额
    public double getBalance() {
        return balance;
    }

    // 获取账户信息
    public void displayInfo() {
        System.out.println("账号: " + accountNumber + ", 户主: " + owner + ", 余额: " + balance);
    }

    // 静态方法：获取银行信息
    public static String getBankInfo() {
        return "银行名称: " + bankName + ", 总账户数: " + totalAccounts;
    }

    // 静态方法：获取总账户数（只读）
    public static int getTotalAccounts() {
        return totalAccounts;
    }
}