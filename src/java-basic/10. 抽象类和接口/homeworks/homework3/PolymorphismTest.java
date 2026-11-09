package homework3;

public class PolymorphismTest {
    public static void main(String[] args) {
        Sparrow s = new Sparrow("小麻雀");
        Penguin p = new Penguin("小企鹅");

        s.sing();
        s.fly();
        s.takeOff();
        s.sleep();

        p.sing();
        p.sleep();
        // ((Flyable) p).fly();  // 这行能编译吗？

        Bird b = new Sparrow("麻雀二号");
        b.sing();
        b.sleep();
        // b.fly();  // 这行能编译吗？

        Flyable f = new Sparrow("飞行麻雀");
        f.fly();
        f.takeOff();
        // f.sing();  // 这行能编译吗？
    }
}