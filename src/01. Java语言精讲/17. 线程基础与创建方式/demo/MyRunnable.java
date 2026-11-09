class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            // 注意：这里要用 Thread.currentThread() 获取当前线程
            System.out.println(Thread.currentThread().getName() + " 打印: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}