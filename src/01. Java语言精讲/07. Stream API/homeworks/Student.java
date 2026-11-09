// Student 类
class Student {
    private String name;
    private int age;
    private double score;
    private String gender;

    public Student(String name, int age, double score, String gender) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getScore() {
        return score;
    }

    public String getGender() {
        return gender;
    }

}
