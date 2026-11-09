public class Service {
    @Log(value = "执行 save 操作", level = 2)
    public void save() {
        System.out.println("保存数据...");
    }

    @Log("执行 delete 操作")   // 当只有一个 value 属性时，可以省略 value=
    public void delete() {
        System.out.println("删除数据...");
    }
}