public class ThreadDemo1 {
    public static void main(String[] args) {
        // 步骤3：创建线程实例
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        // 设置线程名称（方便区分）
        t1.setName("线程A");
        t2.setName("线程B");

        // 步骤4：启动线程（调用 start()）
        t1.start();
        t2.start();

        // 主线程自己的任务
        System.out.println("主线程继续执行自己的任务...");
        System.out.println("主线程结束！");
    }
}