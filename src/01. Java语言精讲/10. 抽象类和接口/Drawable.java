public interface Drawable {
    // 常量（默认为 public static final）
    String TYPE = "图形";

    // 抽象方法（默认为 public abstract）
    void draw();

    static void printMessage(String msg) {
        System.out.println(msg);
    }

    default void printInfo() {
        System.out.println("这是一个图形");
    }
}