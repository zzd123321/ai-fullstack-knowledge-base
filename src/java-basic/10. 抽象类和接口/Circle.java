public class Circle extends Shape implements Drawable {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void draw() {
        System.out.println("画一个圆形");
    }

    // 可以选择重写 printInfo()
    @Override
    public void printInfo() {
        System.out.println("这是一个圆形");
    }
}