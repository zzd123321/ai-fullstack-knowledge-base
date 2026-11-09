package demo.enums;

public class EnumDemo {
    public static void main(String[] args) {
        // 基本使用
        Season season = Season.SUMMER;
        System.out.println("当前季节: " + season);

        // 带字段的枚举
        Status status = Status.SUCCESS;
        System.out.println("状态码: " + status.getCode());
        System.out.println("状态信息: " + status.getMessage());

        // 遍历枚举
        for (Status s : Status.values()) {
            System.out.println(s + " -> " + s.getCode());
        }

        // 通过代码获取枚举
        Status found = Status.fromCode(1);
        System.out.println("找到状态: " + found);

        // 枚举的比较
        System.out.println(status == Status.SUCCESS); // true
        System.out.println(status.equals(Status.SUCCESS)); // true
    }
}