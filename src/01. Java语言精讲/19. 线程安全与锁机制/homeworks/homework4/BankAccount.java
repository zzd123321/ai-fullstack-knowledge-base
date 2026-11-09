package homeworks.homework4;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // 同步方法保证线程安全
    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " 存款 " + amount + "，当前余额：" + balance);
        }
    }

    public synchronized void withdraw(double amount) {
        if (amount > 0) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " 取款 " + amount + "，当前余额：" + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " 取款 " + amount + " 失败，余额不足！当前余额：" + balance);
            }
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(1000);

        // 存款线程：存5次，每次100
        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
                try {
                    Thread.sleep(10); // 模拟操作间隔，增加并发冲突概率
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "存款线程");

        // 取款线程：取5次，每次150
        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(150);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "取款线程");

        depositThread.start();
        withdrawThread.start();

        depositThread.join();
        withdrawThread.join();

        System.out.println("最终余额：" + account.getBalance()); // 预期 750
    }
}