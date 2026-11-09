package homework4;

public class EmployeeTest {
    public static void main(String[] args) {
        // 创建 Employee 数组，存放不同类型的对象
        Employee[] employees = {
                new Manager("M001", "张经理", 12000),
                new Developer("D001", "李开发", 10000),
                new Tester("T001", "王测试", 8000),
                new Manager("M002", "赵总监", 15000),
                new Developer("D002", "刘程序员", 9500)
        };

        System.out.println("=== 员工信息 ===");
        for (Employee e : employees) {
            e.displayInfo();
        }

        System.out.println("\n=== 员工工作状态（多态调用） ===");
        for (Employee e : employees) {
            e.work();  // 动态绑定：运行时调用实际类型的方法
        }

        // 演示：使用父类引用调用子类特有方法需要向下转型
        System.out.println("\n=== 向下转型演示 ===");
        for (Employee e : employees) {
            if (e instanceof Manager) {
                // Manager 类没有额外方法，仅演示 instanceof
                System.out.println(e.name + " 是经理");
            } else if (e instanceof Developer) {
                System.out.println(e.name + " 是开发人员");
            } else if (e instanceof Tester) {
                System.out.println(e.name + " 是测试人员");
            }
        }
    }
}