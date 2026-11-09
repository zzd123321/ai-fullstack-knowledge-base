package homework1;

public class AbstractTest {
    public static void main(String[] args) {
        Animal a1 = new Dog("旺财");
        Animal a2 = new Cat("咪咪");
        a1.makeSound();
        a2.makeSound();
        a1.eat();
        // Animal a3 = new Animal("小动物");  // 这行能编译吗？
    }
}