package homework4;

class MusicPlayer extends MediaPlayer {
    public MusicPlayer(String name) {
        super(name);
    }

    @Override
    public void play() {
        System.out.println(name + " 播放音乐");
    }

    @Override
    public void pause() {
        System.out.println(name + " 暂停音乐");
    }

    @Override
    public void showInfo() {
        System.out.println("我是音乐播放器，名称: " + name);
    }
}