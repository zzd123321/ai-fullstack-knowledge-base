import java.util.*;

/**
 * 作业五：StudentManager 类
 * 使用 List<Map<String, Object>> 存储学生信息（姓名 + 单科成绩）
 * 所有方法均为静态方法
 */
public class StudentManager {
    // 添加学生（姓名 + 成绩）
    public static void addStudent(List<Map<String, Object>> list, String name, int score) {
        Map<String, Object> student = new HashMap<>();
        student.put("name", name);
        student.put("score", score);
        list.add(student);
    }

    // 返回成绩最高的学生（Map 对象）
    public static Map<String, Object> findTopStudent(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Map<String, Object> top = list.get(0);
        for (Map<String, Object> s : list) {
            int currentScore = (int) s.get("score");
            int topScore = (int) top.get("score");
            if (currentScore > topScore) {
                top = s;
            }
        }
        return top;
    }

    // 筛选成绩 >= threshold 的学生，返回新列表
    public static List<Map<String, Object>> filterByScore(List<Map<String, Object>> list, int threshold) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> s : list) {
            if ((int) s.get("score") >= threshold) {
                result.add(s);
            }
        }
        return result;
    }

    // 计算所有学生的平均成绩
    public static double averageScore(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Map<String, Object> s : list) {
            sum += (int) s.get("score");
        }
        return (double) sum / list.size();
    }

    public static void main(String[] args) {
        List<Map<String, Object>> students = new ArrayList<>();

        // 添加学生
        addStudent(students, "Alice", 85);
        addStudent(students, "Bob", 70);
        addStudent(students, "Charlie", 92);
        addStudent(students, "Diana", 60);
        addStudent(students, "Eve", 78);

        // 输出所有学生信息
        System.out.println("所有学生:");
        for (Map<String, Object> s : students) {
            System.out.println(s.get("name") + " -> " + s.get("score"));
        }

        // 最高分
        Map<String, Object> top = findTopStudent(students);
        System.out.println("\n最高分学生: " + top.get("name") + " (" + top.get("score") + "分)");

        // 筛选成绩 >= 80 的学生
        List<Map<String, Object>> filtered = filterByScore(students, 80);
        System.out.println("\n成绩 >= 80 的学生:");
        for (Map<String, Object> s : filtered) {
            System.out.println(s.get("name") + " -> " + s.get("score"));
        }

        // 平均成绩
        System.out.println("\n全体学生平均成绩: " + averageScore(students));
    }
}