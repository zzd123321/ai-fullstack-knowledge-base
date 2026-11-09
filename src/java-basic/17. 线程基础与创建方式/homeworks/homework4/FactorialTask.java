package homework4;

import java.util.concurrent.Callable;

class FactorialTask implements Callable<Long> {
    private int n;

    public FactorialTask(int n) {
        this.n = n;
    }

    @Override
    public Long call() throws Exception {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
            // 模拟计算耗时
            Thread.sleep(50);
        }
        System.out.println("计算完成！");
        return result;
    }
}