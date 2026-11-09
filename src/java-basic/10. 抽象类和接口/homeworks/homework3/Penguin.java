package homework3;

class Penguin extends Bird {
    public Penguin(String name) { super(name); }
    @Override
    public void sing() {
        System.out.println(name + " 嗷嗷叫");
    }
}
