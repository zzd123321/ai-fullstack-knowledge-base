import java.beans.Introspector;

class Student {
    private Integer id;
    private String name;
    private int score;
    private int age;

    public Student(Integer id, String name, int score, int age) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', score=" + score + "}";
    }
}