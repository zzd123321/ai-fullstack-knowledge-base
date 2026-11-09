package homework2;

class Student {
    private String name;
    private int age;

    public Student() {
        this("Unknown", 0);
        System.out.println("无参构造");
    }

    public Student(String name) {
        this(name, 18);
        System.out.println("单参构造");
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("双参构造");
    }

    public String getInfo() {
        return "姓名: " + name + ", 年龄: " + age;
    }
}