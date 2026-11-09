package homework1;

class Cat extends Animal {
    public Cat(String name) {
        super(name);
        System.out.println("Cat 构造: " + name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " 喵喵叫");
    }
}