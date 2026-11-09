package homework4;

import java.lang.reflect.Field;

class Validator {
    public static void validate(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null) {
                    throw new IllegalArgumentException(
                            "Field '" + field.getName() + "' is null but marked @NotNull"
                    );
                }
            }
        }
        System.out.println("Validation passed for: " + obj);
    }

    public static void main(String[] args) {
        User validUser = new User("Alice", "alice@example.com");
        User invalidUser = new User(null, "bob@example.com");

        try {
            System.out.println("--- Validating validUser ---");
            Validator.validate(validUser);

            System.out.println("\n--- Validating invalidUser ---");
            Validator.validate(invalidUser);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
        }
    }
}