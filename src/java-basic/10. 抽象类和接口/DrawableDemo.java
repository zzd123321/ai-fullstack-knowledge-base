
public class DrawableDemo {

    public static void main(String[] args) {

        Drawable d1 = new Circle("红色", 5.0);
        Drawable d2 = new Rectangle("蓝色", 3, 4);

        d1.draw();
        d2.draw();
        d1.printInfo();  // 调用默认方法

        Drawable.printMessage("Hello");  // 调用接口静态方法
    }
}