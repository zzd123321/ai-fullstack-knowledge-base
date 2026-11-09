package homework2;

public class InterfaceTest {
    public static void main(String[] args) {
        Athlete a = new Athlete("张三");
        a.run();
        a.swim();
        a.stop();

        Runner r = new Athlete("李四");
        r.run();
        r.stop();
        // r.swim();  // 这行能编译吗？
    }
}