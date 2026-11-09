import java.lang.reflect.Method;

public class AnnotationReader {
    public static void main(String[] args) throws Exception {
        Method[] methods = Service.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                Log log = method.getAnnotation(Log.class);
                System.out.println("方法: " + method.getName());
                System.out.println("  value: " + log.value());
                System.out.println("  level: " + log.level());
            }
        }
    }
}