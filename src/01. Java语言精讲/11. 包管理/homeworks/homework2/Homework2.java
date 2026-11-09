import static java.lang.Math.PI;
import static java.lang.Math.pow;

public class Homework2 {
    public static void main(String[] args) {
        double radius = 3.0;
        double area = PI * pow(radius, 2);
        System.out.printf("半径为 %.1f 的圆面积: %.2f\n", radius, area);
        
        double circumference = 2 * PI * radius;
        System.out.printf("周长为: %.2f\n", circumference);
    }
}