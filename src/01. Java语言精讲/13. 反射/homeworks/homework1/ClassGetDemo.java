package homework1;

public class ClassGetDemo {
    public static void main(String[] args) throws Exception {
        // 方式1
        Class<?> clazz1 = String.class;
        System.out.println(clazz1.getName());

        // 方式2
        String s = "hello";
        Class<?> clazz2 = s.getClass();
        System.out.println(clazz2.getName());

        // 方式3
        Class<?> clazz3 = Class.forName("java.util.ArrayList");
        System.out.println(clazz3.getName());
    }
}