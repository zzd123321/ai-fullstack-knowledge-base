package demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadLocalDemo {
    // 每个线程拥有独立的 SimpleDateFormat 实例
    private static final ThreadLocal<SimpleDateFormat> dateFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String formatDate(Date date) {
        return dateFormat.get().format(date);
    }

    public static void main(String[] args) {
        Runnable task = () -> {
            System.out.println(Thread.currentThread().getName() + ": " +
                    formatDate(new Date()));
        };
        for (int i = 0; i < 3; i++) {
            new Thread(task).start();
        }
    }
}