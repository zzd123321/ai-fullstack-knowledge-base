import java.util.concurrent.Callable;

class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        System.out.println(Thread.currentThread().getName() + " 开始计算...");
        for (int i = 1; i <= 100; i++) {
            sum += i;
            Thread.sleep(10); // 模拟计算耗时
        }
        System.out.println(Thread.currentThread().getName() + " 计算完成！");
        return sum; // 返回结果
    }
}