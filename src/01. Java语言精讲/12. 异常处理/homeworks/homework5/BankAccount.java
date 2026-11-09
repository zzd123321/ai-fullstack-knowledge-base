package homework5;

class BankAccount {
    private double balance;

    // 构造方法
    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("初始余额不能为负数");
        }
        this.balance = initialBalance;
        System.out.println("账户创建成功，初始余额: " + balance);
    }

    // 存款
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("存款金额必须大于 0，当前: " + amount);
        }
        balance += amount;
        System.out.println("存款成功: +" + amount + "，当前余额: " + balance);
    }

    // 取款
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("取款金额必须大于 0，当前: " + amount);
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("余额不足！需要: " + amount + ", 当前余额: " + balance);
        }
        balance -= amount;
        System.out.println("取款成功: -" + amount + "，当前余额: " + balance);
    }

    // 查询余额
    public double getBalance() {
        return balance;
    }
}