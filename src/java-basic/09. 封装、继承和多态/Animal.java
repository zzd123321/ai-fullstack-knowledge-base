public class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " 正在吃东西");
    }

    public void sleep() {
        System.out.println(name + " 正在睡觉");
    }

    public void sound() {
        System.out.println("动物发出声音");
    }
}