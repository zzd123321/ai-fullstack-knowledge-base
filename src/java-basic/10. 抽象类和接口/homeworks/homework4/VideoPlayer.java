package homework4;

class VideoPlayer extends MediaPlayer {
    public VideoPlayer(String name) {
        super(name);
    }

    @Override
    public void play() {
        System.out.println(name + " 播放视频");
    }

    @Override
    public void pause() {
        System.out.println(name + " 暂停视频");
    }

    @Override
    public void showInfo() {
        System.out.println("我是视频播放器，名称: " + name);
    }
}