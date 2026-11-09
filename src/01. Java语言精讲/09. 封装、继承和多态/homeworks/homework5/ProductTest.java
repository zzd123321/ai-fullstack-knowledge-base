package homework5;

public class ProductTest {
    public static void main(String[] args) {
        System.out.println("=== 创建普通产品 ===");
        Product book = new Product("《Java编程思想》", 89.0, 10);
        book.displayInfo();

        System.out.println("\n=== 创建电子产品 ===");
        ElectronicProduct phone = new ElectronicProduct("智能手机", 2999.0, 8, 12);
        phone.displayInfo();

        System.out.println("\n=== 测试 setter 校验（非法值） ===");
        Product testProduct = new Product("测试产品", -10, -5);
        testProduct.displayInfo();

        System.out.println("\n=== 普通产品售卖 ===");
        book.sell(3);
        book.displayInfo();

        System.out.println("\n--- 尝试售卖超出库存 ---");
        book.sell(10);
        book.displayInfo();

        System.out.println("\n=== 电子产品售卖（少于等于5件，不赠送保修） ===");
        phone.sell(3);
        phone.displayInfo();

        System.out.println("\n=== 电子产品售卖（超过5件，赠送保修） ===");
        ElectronicProduct laptop = new ElectronicProduct("笔记本电脑", 5999.0, 10, 24);
        laptop.displayInfo();
        laptop.sell(6);
        laptop.displayInfo();

        System.out.println("\n=== 多态测试 ===");
        Product p1 = new ElectronicProduct("平板电脑", 3999.0, 5, 12);
        p1.displayInfo();
        p1.sell(4);   // 调用子类重写的 sell（多态）
        p1.displayInfo();
    }
}
