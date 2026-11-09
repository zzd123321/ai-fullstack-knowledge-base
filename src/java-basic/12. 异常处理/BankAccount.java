public class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException("余额不足，当前余额: " + balance);
        }
        balance -= amount;
        System.out.println("取款成功，剩余: " + balance);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(100.0);
        try {
            account.withdraw(150.0);
        } catch (InsufficientBalanceException e) {
            System.out.println("错误: " + e.getMessage());
        }
    }
}