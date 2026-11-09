import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Analysis {

    /**
     * 根据 Predicate 筛选学生（使用 for-each 循环）
     */
    public static List<Student> filterStudent(List<Student> list,
                                              Predicate<Student> predicate) {
        List<Student> result = new ArrayList<>();
        for (Student s : list) {
            if (predicate.test(s)) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * 对每个学生执行 Consumer 操作（使用 for-each 循环）
     */
    public static void processStudent(List<Student> list,
                                      Consumer<Student> consumer) {
        for (Student s : list) {
            consumer.accept(s);
        }
    }

    /**
     * 计算某个属性的平均值（使用 for-each 循环累加）
     */
    public static double calculateAverage(List<Student> list,
                                          Function<Student, Double> extractor) {
        if (list.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Student s : list) {
            sum += extractor.apply(s);
        }
        return sum / list.size();
    }
}