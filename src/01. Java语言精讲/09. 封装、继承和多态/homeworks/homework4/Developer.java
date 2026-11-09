package homework4;

class Developer extends Employee {
    public Developer(String id, String name, double baseSalary) {
        super(id, name, baseSalary);
    }

    @Override
    public void work() {
        System.out.println(name + " 开发人员正在编写代码");
    }
}