package homework4;

class Book {
    // 1. 私有属性
    private String title;
    private String author;
    private String isbn;
    private double price;
    private boolean inStock;

    // 2. 无参构造
    public Book() {
        this("未知标题", "未知作者", "0000000000", 0.0, false);
    }

    // 全参构造
    public Book(String title, String author, String isbn, double price, boolean inStock) {
        this.title = title;
        this.author = author;
        setIsbn(isbn);   // 使用setter校验
        setPrice(price);
        this.inStock = inStock;
    }

    // 3. Getter和Setter（含校验）
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn != null && isbn.length() >= 10) {
            this.isbn = isbn;
        } else {
            System.out.println("ISBN长度必须≥10，已设置为默认值");
            this.isbn = "0000000000";
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        } else {
            System.out.println("价格必须大于0，已设置为0.0");
            this.price = 0.0;
        }
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    // 4. 业务方法
    public void displayInfo() {
        System.out.println("书名: " + title + ", 作者: " + author + ", ISBN: " + isbn +
                ", 价格: ¥" + price + ", 库存状态: " + (inStock ? "有货" : "无货"));
    }

    // 借书：如果有货则借出（设为无货）并返回true，否则返回false
    public boolean borrow() {
        if (inStock) {
            inStock = false;
            System.out.println("《" + title + "》 借书成功！");
            return true;
        } else {
            System.out.println("《" + title + "》 库存不足，无法借阅");
            return false;
        }
    }

    // 还书：将库存设为有货
    public void returnBook() {
        if (!inStock) {
            inStock = true;
            System.out.println("《" + title + "》 还书成功！");
        } else {
            System.out.println("《" + title + "》 本书已在库中，无需归还");
        }
    }
}
