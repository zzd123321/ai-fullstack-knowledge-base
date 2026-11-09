package homework1;

class Person {
    private String name;
    int age;
    protected String address;
    public String phone;

    public Person(String name, int age, String address, String phone) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    public void display() {
        System.out.println(name + ", " + age);
    }
}