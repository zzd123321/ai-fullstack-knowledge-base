package homework4;

import java.lang.reflect.Field;

/**
 * 作业四：通过反射访问私有字段
 */
public class PrivateFieldDemo {
    // 定义 User 类（内部类，方便测试）
    static class User {
        private String username;
        private int age;

        @Override
        public String toString() {
            return "User{username='" + username + "', age=" + age + "}";
        }
    }

    public static void main(String[] args) throws Exception {
        // 1. 获取 User 的 Class 对象
        Class<?> clazz = User.class;

        // 2. 创建 User 对象（使用无参构造）
        User user = (User) clazz.newInstance();

        // 3. 获取 username 字段并设置值
        Field usernameField = clazz.getDeclaredField("username");
        usernameField.setAccessible(true); // 取消私有访问限制
        usernameField.set(user, "admin");

        // 4. 获取 age 字段并设置值
        Field ageField = clazz.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(user, 30);

        // 5. 读取并打印两个字段的值（使用反射）
        String username = (String) usernameField.get(user);
        int age = (int) ageField.get(user);
        System.out.println("username = " + username);
        System.out.println("age = " + age);

        // 也可以直接打印对象（toString 已重写）
        System.out.println(user);
    }
}