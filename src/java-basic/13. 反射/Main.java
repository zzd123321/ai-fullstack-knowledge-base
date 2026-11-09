import com.example.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        Class<Student> clazz1 = Student.class;
        Class<Integer> clazz2 = int.class;  // 基本类型也可以

        Student s = new Student();
        Class<?> clazzA = s.getClass();

        try {
            Class<?> clazzB = Class.forName("com.example.Student");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            Class<?> clazz = Class.forName("com.example.Student");
            // 获取无参构造
            Constructor<?> constructor = clazz.getConstructor();
            Student alice = (Student) constructor.newInstance();

            // 获取有参构造
            Constructor<?> constructor2 = clazz.getConstructor(String.class, int.class);
            Student alice2 = (Student) constructor2.newInstance("Alice", 25);


            // 获取 setName 方法
            Method setName = clazz.getMethod("setName", String.class);
            setName.invoke(alice, "Alice");

            // 获取 getName 方法
            Method getName = clazz.getMethod("getName");
            String name = (String) getName.invoke(alice);
            System.out.println(name);  // Alice


            // 访问私有方法
            Method privateMethod = clazz.getDeclaredMethod("privateMethod");
            privateMethod.setAccessible(true);  // 取消访问检查
            privateMethod.invoke(alice);


            // 访问公有和私有方法
            Field field = clazz.getField("name");
            field.set(alice, "newAlice");
            Object newName = field.get(alice);
            System.out.println(newName);

            Field field2 = clazz.getDeclaredField("age");
            field.setAccessible(true);   // 取消访问检查
            field.set(alice, "30");
            Object newAge = field.get(alice);
            System.out.println(newAge);

        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
