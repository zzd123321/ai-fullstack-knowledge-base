package homework4;

class Manager extends Employee {
    public Manager(String id, String name, double baseSalary) {
        super(id, name, baseSalary);
    }

    @Override
    public void work() {
        System.out.println(name + " 经理正在管理团队");
    }
}