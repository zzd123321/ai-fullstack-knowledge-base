package homework1;

public class TestAccess {
    public static void main(String[] args) {
        Person p = new Person("Alice", 20, "北京", "123456");
        System.out.println(p.name);   // 行1
        System.out.println(p.age);    // 行2
        System.out.println(p.address); // 行3
        System.out.println(p.phone);   // 行4
    }
}