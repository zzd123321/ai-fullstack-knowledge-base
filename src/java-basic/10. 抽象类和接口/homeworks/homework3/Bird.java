package homework3;

abstract class Bird {
    protected String name;
    public Bird(String name) { this.name = name; }
    public abstract void sing();
    public void sleep() {
        System.out.println(name + " 正在睡觉");
    }
}