package homework1;

abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
        System.out.println("Animal 构造: " + name);
    }

    public abstract void makeSound();

    public void eat() {
        System.out.println(name + " 正在吃东西");
    }
}