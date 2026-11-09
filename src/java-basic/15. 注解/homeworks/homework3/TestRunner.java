package homework3;

import java.lang.reflect.Method;

class TestRunner {
    @Testable(priority = 3)
    public void highPriorityTest() {
        System.out.println("Executing high priority test");
    }

    public void normalTest() {
        System.out.println("Executing normal test (no annotation)");
    }

    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        Class<?> clazz = runner.getClass();

        System.out.println("Methods annotated with @Testable:");
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Testable.class)) {
                Testable anno = method.getAnnotation(Testable.class);
                System.out.printf(" - %s (priority = %d)%n", method.getName(), anno.priority());
            }
        }
    }
}