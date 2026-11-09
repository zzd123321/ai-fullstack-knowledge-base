class MyThread extends Thread {
    @Override
    public void run() {
        // 这个线程要执行的任务
        for (int i = 1; i <= 5; i++) {
            // getName() 是 Thread 类的方法，返回线程名称
            System.out.println(getName() + " 打印: " + i);

            // sleep 让当前线程暂停一段时间（模拟耗时操作）
            try {
                Thread.sleep(1000); // 暂停1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + " 执行完毕！");
    }
}