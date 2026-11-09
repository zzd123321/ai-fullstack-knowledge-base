package homeworks.homework3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentSum {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 创建固定大小为5的线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 存储Future结果
        List<Future<Integer>> futures = new ArrayList<>();

        // 创建10个任务，每个计算一个子区间 [i*100+1, (i+1)*100] 的和
        for (int i = 0; i < 10; i++) {
            final int start = i * 100 + 1;
            final int end = (i + 1) * 100;
            Callable<Integer> task = () -> {
                int sum = 0;
                for (int num = start; num <= end; num++) {
                    sum += num;
                }
                System.out.println(Thread.currentThread().getName() + " 计算区间 [" + start + ", " + end + "] 的和 = " + sum);
                return sum;
            };
            futures.add(executor.submit(task));
        }

        // 汇总结果
        int total = 0;
        for (Future<Integer> future : futures) {
            total += future.get(); // 阻塞等待结果
        }

        // 关闭线程池
        executor.shutdown();

        System.out.println("所有区间的总和 = " + total);
        // 验证：1~1000的总和 = 500500
    }
}