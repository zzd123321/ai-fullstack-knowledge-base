package homework1;

public class BookTest {
    public static void main(String[] args) {
        Book b1 = new Book("Java基础", "张三");
        Book b2 = new Book("Python入门", "李四", 39.9);
        b1.display();
        b2.display();
    }
}