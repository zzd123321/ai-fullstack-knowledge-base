package demo.record;

public class RecordDemo {
    public static void main(String[] args) {
        Person person = new Person("Alice", 25);
        // 自动生成的访问器（注意命名：与字段名一致，而非传统的getXxx）
        System.out.println(person.name());
        System.out.println(person.age());
        // 自动生成的toString
        System.out.println(person);  // Person[name=Alice, age=25]
        // 自动生成的equals和hashCode
        Person p2 = new Person("Alice", 25);
        System.out.println(person.equals(p2));  // true
        // 自定义方法
        System.out.println(person.greeting());
    }
}