package homework2;

interface Runner {
    void run();
    default void stop() {
        System.out.println("停止奔跑");
    }
}