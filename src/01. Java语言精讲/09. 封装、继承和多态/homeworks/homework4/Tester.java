package homework4;

class Tester extends Employee {
    public Tester(String id, String name, double baseSalary) {
        super(id, name, baseSalary);
    }

    @Override
    public void work() {
        System.out.println(name + " 测试人员正在执行测试");
    }
}