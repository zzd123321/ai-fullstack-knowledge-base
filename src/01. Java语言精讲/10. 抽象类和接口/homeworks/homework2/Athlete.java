package homework2;

class Athlete implements Runner, Swimmer {
    private String name;

    public Athlete(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " 正在跑步");
    }

    @Override
    public void swim() {
        System.out.println(name + " 正在游泳");
    }

    @Override
    public void stop() {
        System.out.println(name + " 停止运动");
        Runner.super.stop();
        Swimmer.super.stop();
    }
}