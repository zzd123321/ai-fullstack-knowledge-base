
public class Main {

    public static void main(String[] args) {
        // 向上转型
        Animal a1 = new Dog("旺财");   // 向上转型
        Animal a2 = new Cat("咪咪");

        a1.sound();   // 调用实际对象的 sound()，输出 "汪汪"
        a2.sound();   // 输出 "喵喵"


        // 向下转型
        Animal a = new Dog("旺财");
        if (a instanceof Dog) {
            Dog d = (Dog) a;   // 向下转型
            d.bark();          // 调用 Dog 特有的方法
        }
    }
}