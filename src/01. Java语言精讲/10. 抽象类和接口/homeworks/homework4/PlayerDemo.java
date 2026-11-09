package homework4;

public class PlayerDemo {
    public static void main(String[] args) {
        // 使用 Playable 类型引用
        Playable p1 = new MusicPlayer("网易云音乐");
        Playable p2 = new VideoPlayer("腾讯视频");

        System.out.println("=== 音乐播放器 ===");
        p1.play();
        p1.pause();
        p1.stop();  // 调用默认方法
        // showInfo() 无法通过 Playable 调用，需要向下转型
        if (p1 instanceof MediaPlayer) {
            ((MediaPlayer) p1).showInfo();
        }

        System.out.println("\n=== 视频播放器 ===");
        p2.play();
        p2.pause();
        p2.stop();
        if (p2 instanceof MediaPlayer) {
            ((MediaPlayer) p2).showInfo();
        }

        // 也可以使用 MediaPlayer 类型引用
        System.out.println("\n=== 使用 MediaPlayer 类型 ===");
        MediaPlayer m1 = new MusicPlayer("QQ音乐");
        m1.play();
        m1.pause();
        m1.stop();
        m1.showInfo();
    }
}