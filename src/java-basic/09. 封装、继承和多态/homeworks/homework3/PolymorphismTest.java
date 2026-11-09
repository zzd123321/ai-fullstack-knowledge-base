package homework3;

public class PolymorphismTest {
    public static void main(String[] args) {
        Shape s1 = new Circle();
        Shape s2 = new Square();
        Shape[] shapes = {s1, s2, new Shape()};
        for (Shape s : shapes) {
            s.draw();
        }
    }
}