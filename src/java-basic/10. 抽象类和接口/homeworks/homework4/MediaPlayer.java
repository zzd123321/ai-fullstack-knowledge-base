package homework4;

abstract class MediaPlayer implements Playable {
    protected String name;

    public MediaPlayer(String name) {
        this.name = name;
    }

    // 抽象方法：显示播放器信息
    public abstract void showInfo();

    // 重写 stop() 方法
    @Override
    public void stop() {
        System.out.println(name + " 停止播放");
    }
}
