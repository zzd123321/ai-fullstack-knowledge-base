package homework4;

import java.util.concurrent.FutureTask;

public class FactorialDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("正在计算阶乘...");

        // 创建 Callable 任务并包装为 FutureTask
        FactorialTask task = new FactorialTask(10);
        FutureTask<Long> futureTask = new FutureTask<>(task);

        // 创建线程并启动
        Thread t = new Thread(futureTask, "阶乘计算线程");
        t.start();

        // 获取结果（会阻塞直到计算完成）
        Long result = futureTask.get();
        System.out.println("10! = " + result);
    }
}