package homework5;

class ElectronicProduct extends Product {
    private int warrantyPeriod;  // 保修期（月）

    public ElectronicProduct(String name, double price, int stock, int warrantyPeriod) {
        super(name, price, stock);
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        if (warrantyPeriod >= 0) {
            this.warrantyPeriod = warrantyPeriod;
        } else {
            System.out.println("保修期不能为负数，已设置为 0");
            this.warrantyPeriod = 0;
        }
    }

    // 重写 sell 方法：售出超过 5 件赠送 1 个月保修
    @Override
    public boolean sell(int quantity) {
        boolean result = super.sell(quantity);
        if (result && quantity > 5) {
            warrantyPeriod += 1;
            System.out.println("🎉 售出 " + quantity + " 件，赠送 1 个月保修！当前保修: " + warrantyPeriod + " 个月");
        }
        return result;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  保修期: " + warrantyPeriod + " 个月");
    }
}