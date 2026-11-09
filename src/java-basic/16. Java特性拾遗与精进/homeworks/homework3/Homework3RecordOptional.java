package homeworks.homework3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Homework3RecordOptional {

    public record User(String id, String name) {}

    public static class UserService {
        private final Map<String, User> users = new HashMap<>();

        public UserService() {
            users.put("U001", new User("U001", "张三"));
            users.put("U002", new User("U002", "李四"));
            users.put("U003", new User("U003", "王五"));
            users.put("U004", new User("U004", "赵六"));
        }

        public Optional<User> findById(String id) {
            return Optional.ofNullable(users.get(id));
        }
    }

    public static void main(String[] args) {
        UserService service = new UserService();

        // 查找存在的用户
        System.out.println("查找 U002：");
        service.findById("U002").ifPresentOrElse(
                user -> System.out.println("找到用户: " + user),
                () -> System.out.println("用户不存在")
        );

        // 查找不存在的用户
        System.out.println("\n查找 U999：");
        service.findById("U999").ifPresentOrElse(
                user -> System.out.println("找到用户: " + user),
                () -> System.out.println("用户不存在")
        );

        // 使用 map 将姓名转为大写
        System.out.println("\nU001 姓名大写：");
        service.findById("U001")
                .map(User::name)
                .map(String::toUpperCase)
                .ifPresent(System.out::println);

        // 使用 orElse 提供默认值
        String nameOrDefault = service.findById("U999")
                .map(User::name)
                .orElse("默认用户");
        System.out.println("\norElse 结果: " + nameOrDefault);
    }
}