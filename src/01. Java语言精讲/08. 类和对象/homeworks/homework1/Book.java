package homework1;

class Book {
    String title;
    String author;
    double price;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        System.out.println("调用构造方法(1)");
    }

    public Book(String title, String author, double price) {
        this(title, author);
        this.price = price;
        System.out.println("调用构造方法(2)");
    }

    public void display() {
        System.out.println("《" + title + "》 作者: " + author + " 价格: ¥" + price);
    }
}
