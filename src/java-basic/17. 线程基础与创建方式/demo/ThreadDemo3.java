import java.util.concurrent.FutureTask;

public class ThreadDemo3 {
    public static void main(String[] args) throws Exception {
        // 步骤3：创建 Callable 实例并包装为 FutureTask
        MyCallable task = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(task);

        // 步骤4 + 5：创建线程并启动
        Thread t = new Thread(futureTask, "计算线程");
        t.start();

        System.out.println("主线程在等待计算结果时，可以做其他事情...");
        Thread.sleep(500);
        System.out.println("主线程做完了其他事情，现在来获取结果...");

        // 步骤6：获取结果（如果计算未完成，会阻塞等待）
        Integer result = futureTask.get();
        System.out.println("1+2+...+100 = " + result);
    }
}