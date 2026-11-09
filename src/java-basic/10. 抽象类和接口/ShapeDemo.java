public class ShapeDemo {
    public static void main(String[] args) {
        // Shape s = new Shape("red");  // 编译错误：不能实例化抽象类

        Shape[] shapes = {
                new Circle("红色", 5.0),
                new Rectangle("蓝色", 3.0, 4.0)
        };

        for (Shape s : shapes) {
            System.out.println("面积: " + s.area());
            System.out.println("周长: " + s.perimeter());
            s.displayColor();
        }
    }
}