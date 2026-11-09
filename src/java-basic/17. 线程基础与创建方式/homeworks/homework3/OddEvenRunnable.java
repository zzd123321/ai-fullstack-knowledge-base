package homework3;

class OddEvenRunnable implements Runnable {
    private int start;      // 起始数字（奇数从1开始，偶数从2开始）
    private String name;    // 线程名称

    public OddEvenRunnable(int start, String name) {
        this.start = start;
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = start; i <= 20; i += 2) {
            System.out.println(name + ": " + i);
            try {
                // 每次打印后休眠 150ms，让另一个线程有机会执行
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}