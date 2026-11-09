import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDemo {

    public static void main(String[] args) {
        // 生成一组 Student 对象（示例数据）
        List<Student> students = Arrays.asList(
                new Student(1, "Alice", 92, 18),
                new Student(2, "Bob", 78, 19),
                new Student(3, "Charlie", 85, 18),
                new Student(4, "Diana", 60, 19),
                new Student(5, "Eve", 95, 19)
        );

        // 传统的 for 循环方式
        List<String> namesForLoop = new ArrayList<>();
        for (Student s : students) {
            if (s.getScore() >= 80) {
                namesForLoop.add(s.getName().toUpperCase());
            }
        }
        System.out.println("For 循环结果: " + namesForLoop);

        // Stream 方式（更简洁、更可读）
        List<String> namesStream = students.stream()
                .filter(s -> s.getScore() >= 80)
                .map(s -> s.getName().toUpperCase())
                .collect(Collectors.toList());
        System.out.println("Stream 结果: " + namesStream);


        // 收集为 Map（需指定键和值）
        Map<Integer, String> map = students.stream()
                .collect(Collectors.toMap(Student::getId, Student::getName));
        System.out.println(map);


        // 提取对象属性
        students.stream()
                .map(Student::getName)  // 提取姓名
                .forEach(System.out::println);


        // 筛选出成绩 ≥ 90 的学生姓名，并转为大写，按姓名排序，取前5个
        List<String> topNames = students.stream()
                .filter(s -> s.getScore() >= 90)
                .map(Student::getName)
                .map(String::toUpperCase)
                .sorted()
                .limit(5)
                .toList();
        System.out.println(topNames);


        // 按年龄分组，统计每组的平均分
        Map<Integer, Double> avgByAge = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getAge,
                        Collectors.averagingDouble(Student::getScore)
                ));
        System.out.println(avgByAge);

    }
}