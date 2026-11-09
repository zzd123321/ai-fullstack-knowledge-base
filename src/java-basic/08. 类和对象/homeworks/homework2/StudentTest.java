package homework2;

public class StudentTest {
    public static void main(String[] args) {
        Student s1 = new Student();
        Student s2 = new Student("Alice");
        System.out.println(s1.getInfo());
        System.out.println(s2.getInfo());
    }
}