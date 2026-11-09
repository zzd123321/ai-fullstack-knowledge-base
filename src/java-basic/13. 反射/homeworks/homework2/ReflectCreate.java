package homework2;

public class ReflectCreate {
    public static void main(String[] args) throws Exception {
        // 1. 获取 Person 的 Class 对象
        Class<?> clazz = ________;

        // 2. 获取带参构造方法
        Constructor<?> constructor = ________;

        // 3. 使用构造方法创建对象
        Object obj = ________;

        // 4. 调用 toString 方法并打印
        System.out.println(obj);
    }
}