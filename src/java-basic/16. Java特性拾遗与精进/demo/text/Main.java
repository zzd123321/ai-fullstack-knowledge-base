package demo.text;

public class Main {
    public static void main(String[] args) {

        // 基本文本块
        String json = """
              {
                  "name": "Alice",
                  "age": 25,
                  "city": "Beijing"
              }
              """;
        System.out.println(json);

        // 嵌入表达式（使用 String.format 或 formatted）
        String name = "Bob";
        int age = 30;
        String message = """
                 姓名: %s
                 年龄: %d
                 状态: 活跃
                 """.formatted(name, age);
        System.out.println(message);

        // 行末去除换行（使用 \）
        String singleLine = """
                    这是一行 \
                    虽然写了两行 \
                    但实际是一行
                    """;
        System.out.println(singleLine);  // "这是一行 虽然写了两行 但实际是一行"
    }
}
