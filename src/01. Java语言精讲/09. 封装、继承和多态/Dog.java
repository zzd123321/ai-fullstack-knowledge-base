public class Dog extends Animal {
    private String breed;

    public Dog(String name) {
        super(name);  // 调用父类构造方法
    }

    public Dog(String name, String breed) {
        super(name);      // 调用父类构造方法（必须是第一条语句）
        this.breed = breed;
    }

    public void bark() {
        System.out.println(name + " 汪汪叫");
    }

    public void display() {
        System.out.println("名字: " + name);        // 继承自父类的属性
        super.eat();                    // 调用父类方法
        System.out.println("品种: " + breed);
    }

    @Override
    public void sound() {
        System.out.println("汪汪");
    }
}