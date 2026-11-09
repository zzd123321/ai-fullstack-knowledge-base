public class ThreadDemo2Lambda {
    public static void main(String[] args) {
        // 用 Lambda 表达式直接定义任务
        Runnable task = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " 打印: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(task, "线程A").start();
        new Thread(task, "线程B").start();

        // 更简洁的写法：直接传 Lambda 表达式
        new Thread(() -> {
            System.out.println("这是匿名线程");
        }).start();
    }
}