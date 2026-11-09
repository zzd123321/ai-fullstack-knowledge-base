package homework3;

class Sparrow extends Bird implements Flyable {
    public Sparrow(String name) { super(name); }
    @Override
    public void sing() {
        System.out.println(name + " 叽叽喳喳");
    }
    @Override
    public void fly() {
        System.out.println(name + " 在飞翔");
    }
    @Override
    public void takeOff() {
        System.out.println(name + " 振翅起飞");
    }
}