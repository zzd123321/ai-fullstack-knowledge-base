import java.util.*;
import java.util.stream.Collectors;

public class Homework4 {
    public static void main(String[] args) {
        // 创建至少10个学生
        List<Student> students = Arrays.asList(
            new Student("Alice", 20, 85.5, "F"),
            new Student("Bob", 22, 72.0, "M"),
            new Student("Charlie", 19, 90.0, "M"),
            new Student("Diana", 21, 68.5, "F"),
            new Student("Eve", 20, 95.0, "F"),
            new Student("Frank", 23, 78.0, "M"),
            new Student("Grace", 22, 88.5, "F"),
            new Student("Henry", 18, 60.0, "M"),
            new Student("Ivy", 21, 92.0, "F"),
            new Student("Jack", 20, 82.0, "M")
        );

        // 任务1：成绩 >= 80 的学生姓名，按成绩降序
        System.out.println("=== 任务1：成绩≥80的学生（降序） ===");
        List<String> highScoreNames = students.stream()
                .filter(s -> s.getScore() >= 80)
                .sorted(Comparator.comparingDouble(Student::getScore).reversed())
                .map(Student::getName)
                .collect(Collectors.toList());
        highScoreNames.forEach(System.out::println);

        // 任务2：平均成绩
        System.out.println("\n=== 任务2：平均成绩 ===");
        double avgScore = students.stream()
                .mapToDouble(Student::getScore)
                .average()
                .orElse(0.0);
        System.out.printf("平均成绩: %.2f\n", avgScore);

        // 任务3：按性别分组，统计平均年龄
        System.out.println("\n=== 任务3：按性别分组平均年龄 ===");
        Map<String, Double> avgAgeByGender = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getGender,
                        Collectors.averagingInt(Student::getAge)
                ));
        avgAgeByGender.forEach((gender, avgAge) -> 
                System.out.println(gender + " -> " + avgAge));

        // 任务4：成绩最高的学生信息
        System.out.println("\n=== 任务4：成绩最高的学生 ===");
        students.stream()
                .max(Comparator.comparingDouble(Student::getScore))
                .ifPresent(System.out::println);

        // 任务5：所有不同年龄（去重，升序）
        System.out.println("\n=== 任务5：不同年龄（升序） ===");
        List<Integer> distinctAges = students.stream()
                .map(Student::getAge)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(distinctAges);
    }
}