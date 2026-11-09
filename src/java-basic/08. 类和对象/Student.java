public class Student {
    // 1. 属性（成员变量）
    String name;
    int age;
    String studentId;

    // 无参构造方法
    public Student() {
        System.out.println("调用无参构造方法");
    }

    // 有参构造方法
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("调用有参构造方法");
    }

    public Student(String name, int age, String studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }


    // 2. 方法（行为）
    public void study() {
        System.out.println(name + " 正在学习");
    }

    public void introduce() {
        System.out.println("我是 " + name + "，今年 " + age + " 岁");
    }
}


