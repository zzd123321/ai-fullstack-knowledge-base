public class ThreadInfo {
    public static void main(String[] args) {
        // 获取当前正在执行的线程对象
        Thread current = Thread.currentThread();
        System.out.println("当前线程名称: " + current.getName());
        System.out.println("线程唯一ID: " + current.getId());
        System.out.println("线程优先级: " + current.getPriority());
    }
}