public class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * 封装存款操作，不要直接修改余额字段
     */
    public void deposit(double amount) {
        if (isPositive(amount)) {
            balance += amount;
            printTransaction("存入", amount);
        } else {
            System.out.println("存款金额必须大于 0");
        }
    }

    /**
     * 封装提取操作，不要直接修改余额字段
     */
    public void withdraw(double amount) {
        if (isPositive(amount) && amount <= balance) {
            balance -= amount;
            printTransaction("取出", amount);
        } else {
            System.out.println("余额不足或金额无效");
        }
    }

    /**
     * 检查金额是否为正数
     */
    private boolean isPositive(double amount) {
        return amount > 0;
    }

    /**
     * 打印交易操作及当前余额
     */
    private void printTransaction(String operation, double amount) {
        System.out.println(operation + " " + amount + "，当前余额: " + balance);
    }
}