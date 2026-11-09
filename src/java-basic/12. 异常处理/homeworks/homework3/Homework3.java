package homework3;

/**
 * 测试类
 */
public class Homework3 {
    public static void main(String[] args) {
        Student s1 = new Student("Alice");
        Student s2 = new Student("Bob");

        // 测试1：设置合法分数
        try {
            s1.setScore(85);
            System.out.println("学生: " + s1.getName() + ", 成绩: " + s1.getScore());
        } catch (InvalidScoreException e) {
            System.out.println("错误: " + e.getMessage());
        }

        // 测试2：设置非法分数
        try {
            s2.setScore(-10);
        } catch (InvalidScoreException e) {
            System.out.println("错误: " + e.getMessage());
        }

        // 测试3：再次测试非法分数
        try {
            s2.setScore(150);
        } catch (InvalidScoreException e) {
            System.out.println("错误: " + e.getMessage());
        }
    }
}