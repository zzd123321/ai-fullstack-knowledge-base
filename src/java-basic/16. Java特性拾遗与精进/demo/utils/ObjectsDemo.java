package demo.utils;

import java.util.Objects;

public class ObjectsDemo {
    public static void main(String[] args) {
        // 1. equals：安全比较（处理null）
        System.out.println(Objects.equals("hello", "hello"));  // true
        System.out.println(Objects.equals(null, "hello"));     // false
        System.out.println(Objects.equals("hello", null));     // false

        // 2. hashCode：安全获取hashCode
        System.out.println(Objects.hashCode("hello"));  // 99162322
        System.out.println(Objects.hashCode(null));     // 0

        // 3. hash：计算多个字段的hashCode
        System.out.println(Objects.hash("Alice", 25));  // 组合hash

        // 4. requireNonNull：检查非空，为空则抛异常
        String name = null;
        // String result = Objects.requireNonNull(name);  // NullPointerException
        // String result2 = Objects.requireNonNull(name, "名称不能为空");

        // 5. toString：安全toString（null返回"null"）
        System.out.println(Objects.toString(null));  // "null"
        System.out.println(Objects.toString(null, "默认值"));  // "默认值"

        // 6. compare：安全比较
        String a = "apple";
        String b = "banana";
        System.out.println(Objects.compare(a, b, String::compareTo));  // -1

        // 7. isNull / nonNull
        System.out.println(Objects.isNull(null));    // true
        System.out.println(Objects.nonNull(null));   // false
    }
}