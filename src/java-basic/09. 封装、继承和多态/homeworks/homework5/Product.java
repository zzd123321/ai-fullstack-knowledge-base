package homework5;

class Product {
    private String name;
    private double price;
    private int stock;

    // 构造方法
    public Product(String name, double price, int stock) {
        this.name = name;
        setPrice(price);   // 使用 setter 进行校验
        setStock(stock);   // 使用 setter 进行校验
    }

    // Getter 方法
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setter 方法（含校验）
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("警告: 价格不能为负数，已设置为 0");
            this.price = 0;
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            System.out.println("警告: 库存不能为负数，已设置为 0");
            this.stock = 0;
        }
    }

    // 售卖方法
    public boolean sell(int quantity) {
        if (quantity <= 0) {
            System.out.println("售卖数量必须大于 0");
            return false;
        }
        if (quantity <= stock) {
            stock -= quantity;
            System.out.println("成功售出 " + quantity + " 件 " + name);
            return true;
        } else {
            System.out.println("库存不足！当前库存: " + stock + "，需要: " + quantity);
            return false;
        }
    }

    // 显示产品信息
    public void displayInfo() {
        System.out.println("产品: " + name + ", 价格: ¥" + price + ", 库存: " + stock);
    }
}