package homework3;

/**
 * 学生类
 */
class Student {
    private String name;
    private int score;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) throws InvalidScoreException {
        if (score < 0 || score > 100) {
            throw new InvalidScoreException("分数必须在 0~100 之间，当前值: " + score);
        }
        this.score = score;
        System.out.println(name + " 的成绩已设置为: " + score);
    }
}