package homework4;

class Employee {
    protected String id;
    protected String name;
    protected double baseSalary;

    public Employee(String id, String name, double baseSalary) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public void work() {
        System.out.println(name + " 员工正在工作");
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", 姓名: " + name + ", 薪资: " + baseSalary);
    }
}
