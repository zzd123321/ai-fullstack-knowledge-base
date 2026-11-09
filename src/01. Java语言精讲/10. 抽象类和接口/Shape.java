public abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    // 抽象方法（没有方法体）：计算面积
    public abstract double area();

    // 抽象方法（没有方法体）：计算周长
    public abstract double perimeter();

    // 普通方法（子类可继承）
    public void displayColor() {
        System.out.println("颜色: " + color);
    }
}