public class Person {
    private String name;
    private int age;

    // 实例初始化块：每次创建对象时执行
    {
        System.out.println("执行实例初始化块");
        this.age = 0;
    }

    // 静态初始化块：类加载时执行一次
    static {
        System.out.println("执行静态初始化块");
    }

    public Person(String name) {
        this.name = name;
        System.out.println("执行构造方法");
    }

    public static void main(String[] args) {
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
    }
}