package homework2;

class Vehicle {
    private String brand;

    public Vehicle(String brand) {
        this.brand = brand;
        System.out.println("Vehicle 构造: " + brand);
    }

    public void start() {
        System.out.println("Vehicle 启动");
    }
}