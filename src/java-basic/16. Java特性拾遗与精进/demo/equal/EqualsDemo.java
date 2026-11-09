package demo.equal;

import java.util.Objects;

public class EqualsDemo {
    public static void main(String[] args) {
        // ========== 基本类型 ==========
        int a = 5;
        int b = 5;
        System.out.println(a == b);  // true（比较值）

        // ========== 引用类型 ==========
        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String("hello");

        System.out.println(s1 == s2);  // true（字符串常量池复用）
        System.out.println(s1 == s3);  // false（不同对象）
        System.out.println(s1.equals(s3));  // true（内容相同）

        // ========== 自定义类 ==========
        Person p1 = new Person("Alice", 25);
        Person p2 = new Person("Alice", 25);
        System.out.println(p1 == p2);  // false（不同对象）
        System.out.println(p1.equals(p2));  // true（如果重写了equals）

        // ========== 重要：Integer 缓存 ==========
        Integer i1 = 127;
        Integer i2 = 127;
        System.out.println(i1 == i2);  // true（-128~127缓存）

        Integer i3 = 128;
        Integer i4 = 128;
        System.out.println(i3 == i4);  // false（超出缓存范围）
        System.out.println(i3.equals(i4));  // true

        // ========== null 处理 ==========
        String str = null;
        // str.equals("hello");  // NullPointerException!
        System.out.println("hello".equals(str));  // false（安全）
        System.out.println(Objects.equals(str, "hello"));  // false（安全）
    }
}