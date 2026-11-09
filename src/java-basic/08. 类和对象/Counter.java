public class Counter {
    // 实例变量：每个对象拥有独立副本
    private int instanceCount;

    // 静态变量：所有对象共享
    public static int totalCount = 0;

    public Counter() {
        instanceCount++;
        totalCount++;
    }

    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();

        System.out.println(c1.instanceCount);  // 1
        System.out.println(c2.instanceCount);  // 1
        System.out.println(c3.instanceCount);  // 1

        System.out.println(Counter.totalCount); // 3（类名访问）
        System.out.println(c1.totalCount);      // 3（对象访问，不推荐）
    }
}