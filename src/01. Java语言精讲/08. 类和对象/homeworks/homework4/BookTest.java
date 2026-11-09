package homework4;

public class BookTest {
    public static void main(String[] args) {
        // 创建3本图书
        Book b1 = new Book("Java核心技术", "Cay Horstmann", "9787111609133", 99.0, true);
        Book b2 = new Book("Python编程", "Eric Matthes", "9787115543678", 79.0, true);
        Book b3 = new Book("算法导论", "Thomas Cormen", "9787115127548", 128.0, false);

        System.out.println("=== 初始图书信息 ===");
        b1.displayInfo();
        b2.displayInfo();
        b3.displayInfo();

        System.out.println("\n=== 借书操作 ===");
        b1.borrow();  // 有货，借出
        b1.borrow();  // 再次借出，应失败
        b3.borrow();  // 无货，失败

        System.out.println("\n=== 借出后库存状态 ===");
        b1.displayInfo();
        b3.displayInfo();

        System.out.println("\n=== 还书操作 ===");
        b1.returnBook(); // 还书成功
        b1.returnBook(); // 再次还书，提示已在库
        b3.returnBook(); // 无货，还书成功

        System.out.println("\n=== 最终库存状态 ===");
        b1.displayInfo();
        b3.displayInfo();

        // 测试setter校验
        System.out.println("\n=== 测试setter校验 ===");
        Book b4 = new Book();
        b4.setPrice(-10);
        b4.setIsbn("123");
        b4.displayInfo();
    }
}