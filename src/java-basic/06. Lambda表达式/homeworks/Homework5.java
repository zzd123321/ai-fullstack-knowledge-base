import java.util.List;


public class Homework5
{
    public static void main(String[] args) {
        List<Student> students = List.of(
            new Student("张三", 20, 85.5, "Male"),
            new Student("李四", 21, 72.0, "Female"),
            new Student("王五", 19, 90.0, "Male"),
            new Student("赵六", 22, 65.5, "Female"),
            new Student("孙七", 20, 78.0, "Male"),
            new Student("周八", 21, 88.5, "Female"),
            new Student("吴九", 19, 95.0, "Male"),
            new Student("郑十", 22, 60.0, "Female")
        );

        // 筛选成绩 >= 80
        List<Student> highScore = Analysis.filterStudent(students, s -> s.getScore() >= 80);
        System.out.println("成绩 >= 80 的学生人数: " + highScore.size());

        // 打印姓名和成绩
        System.out.println("姓名\t成绩");
        Analysis.processStudent(highScore, s ->
            System.out.println(s.getName() + "\t" + s.getScore())
        );

        // 计算平均分
        double avg = Analysis.calculateAverage(students, Student::getScore);
        System.out.printf("全体学生平均分: %.2f%n", avg);
    }
}