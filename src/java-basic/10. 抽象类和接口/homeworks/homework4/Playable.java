package homework4;

interface Playable {
    void play();
    void pause();

    default void stop() {
        System.out.println("停止播放");
    }
}