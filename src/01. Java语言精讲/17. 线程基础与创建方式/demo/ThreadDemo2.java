public class ThreadDemo2 {
    public static void main(String[] args) {
        // 步骤3：创建 Runnable 实例
        MyRunnable task = new MyRunnable();

        // 步骤4：创建 Thread，传入 task
        Thread t1 = new Thread(task, "线程A");
        Thread t2 = new Thread(task, "线程B");

        // 步骤5：启动
        t1.start();
        t2.start();
    }
}