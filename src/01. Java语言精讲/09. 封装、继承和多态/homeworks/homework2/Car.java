package homework2;

class Car extends Vehicle {
    private String model;

    public Car(String brand, String model) {
        super(brand);
        this.model = model;
        System.out.println("Car 构造: " + model);
    }

    @Override
    public void start() {
        super.start();
        System.out.println("Car 启动");
    }
}