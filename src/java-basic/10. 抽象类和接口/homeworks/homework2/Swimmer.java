package homework2;

interface Swimmer {
    void swim();
    default void stop() {
        System.out.println("停止游泳");
    }
}