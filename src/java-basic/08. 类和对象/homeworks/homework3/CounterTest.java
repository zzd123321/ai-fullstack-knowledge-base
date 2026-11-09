package homework3;

public class CounterTest {
    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();

        System.out.println("c1.instanceCount = " + c1.getInstanceCount());
        System.out.println("c2.instanceCount = " + c2.getInstanceCount());
        System.out.println("c3.instanceCount = " + c3.getInstanceCount());
        System.out.println("总对象数: " + Counter.getTotalCount());
    }
}