package homework1;

class Dog extends Animal {
    public Dog(String name) {
        super(name);
        System.out.println("Dog 构造: " + name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " 汪汪叫");
    }
}
