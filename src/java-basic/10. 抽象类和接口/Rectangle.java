public class Rectangle extends Shape implements Drawable, Comparable<Rectangle> {
    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public void draw() {
        System.out.println("画一个矩形");
    }

    @Override
    public int compareTo(Rectangle o) {
        return Double.compare(this.area(), o.area());
    }
}