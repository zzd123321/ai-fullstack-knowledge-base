public class Box<T> {
    private T content;

    public void set(T content) {
        this.content = content;
    }

    public T get() {
        return content;
    }

    public static void main(String[] args) {
        Box<String> stringBox = new Box<String>();
        stringBox.set("Hello");
        System.out.println(stringBox.get());  // Hello

        Box<Integer> intBox = new Box<>();
        intBox.set(123);
        System.out.println(intBox.get());     // 123
    }
}