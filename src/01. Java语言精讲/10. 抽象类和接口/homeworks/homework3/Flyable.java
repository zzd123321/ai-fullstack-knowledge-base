package homework3;

interface Flyable {
    void fly();
    default void takeOff() {
        System.out.println("准备起飞");
    }
}
