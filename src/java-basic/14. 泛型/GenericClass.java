public class GenericClass<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public <U> U convert(U input) {
        return input;
    }

    // 简化的测试 main
    public static void main(String[] args) {
        GenericClass<String> holder = new GenericClass<>(); // T = String
        System.out.println(holder.getValue());              // null，无 setter

        Integer num = holder.convert(123);                 // U 自动推断为 Integer
        System.out.println(num);                           // 123

        String text = holder.convert("Hello");             // U 推断为 String
        System.out.println(text);                          // Hello
    }
}