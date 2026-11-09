public class StudentDemo {
    public static void main(String[] args) {
        // 创建 Student 对象
        Student s1 = new Student();
        Student s2 = new Student();

        // 给属性赋值
        s1.name = "Alice";
        s1.age = 20;
        s1.studentId = "2024001";

        s2.name = "Bob";
        s2.age = 22;
        s2.studentId = "2024002";

        // 调用方法
        s1.introduce();  // 我是 Alice，今年 20 岁
        s1.study();      // Alice 正在学习

        s2.introduce();  // 我是 Bob，今年 22 岁

        Student s3 = null;      // null 表示不指向任何对象
        // s3.study();          // 会抛出 NullPointerException
        if (s3 != null) {
            s3.study();         // 安全调用
        }
    }
}

