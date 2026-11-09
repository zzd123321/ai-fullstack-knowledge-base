package homework4;

public class MultiThreadStatistics {
    public static void main(String[] args) throws InterruptedException {
        // 使用长度为 3 的数组存储每个线程的计算结果（线程安全，无竞态）
        long[] results = new long[3];

        // 线程A：计算 1-100 的和
        Thread threadA = new Thread(() -> {
            long sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += i;
            }
            results[0] = sum;
        });

        // 线程B：计算 1-100 的平方和
        Thread threadB = new Thread(() -> {
            long sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += (long) i * i; // 防止 int 溢出
            }
            results[1] = sum;
        });

        // 线程C：计算 1-100 的立方和
        Thread threadC = new Thread(() -> {
            long sum = 0;
            for (int i = 1; i <= 100; i++) {
                sum += (long) i * i * i; // 防止 int 溢出
            }
            results[2] = sum;
        });

        // 启动所有线程
        threadA.start();
        threadB.start();
        threadC.start();

        // 主线程等待每个工作线程完成
        threadA.join();
        threadB.join();
        threadC.join();

        // 输出各个结果
        System.out.println("线程A: 1-100 的和 = " + results[0]);
        System.out.println("线程B: 1-100 的平方和 = " + results[1]);
        System.out.println("线程C: 1-100 的立方和 = " + results[2]);

        // 汇总
        long total = results[0] + results[1] + results[2];
        System.out.println("所有统计结果总和 = " + total);
    }
}