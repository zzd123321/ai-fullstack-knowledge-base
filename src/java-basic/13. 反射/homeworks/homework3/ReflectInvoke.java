package homework3;

import java.lang.reflect.Method;

/**
 * 作业三：通过反射调用 String 的 charAt 方法
 */
public class ReflectInvoke {
    public static void main(String[] args) throws Exception {
        // 1. 获取 String 的 Class 对象
        Class<String> clazz = String.class;

        // 2. 获取 charAt(int index) 方法
        Method charAtMethod = clazz.getMethod("charAt", int.class);

        // 3. 创建字符串对象
        String str = "hello";

        // 4. 调用 charAt(1) 方法（参数 index=1）
        char result = (char) charAtMethod.invoke(str, 1);

        // 5. 打印结果
        System.out.println("charAt(1) = " + result); // 输出 e
    }
}