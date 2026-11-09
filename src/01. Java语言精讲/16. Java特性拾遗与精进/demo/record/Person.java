package demo.record;

public record Person(String name, int age) {
    // 可以添加自定义构造方法（校验逻辑）
    public Person {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("年龄必须在0~150之间");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("姓名不能为空");
        }
    }

    // 可以添加自定义方法
    public String greeting() {
        return "你好，我是" + name + "，今年" + age + "岁";
    }
}