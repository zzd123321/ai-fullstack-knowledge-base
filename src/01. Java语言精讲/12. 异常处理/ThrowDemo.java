import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ThrowDemo {
    public static void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("年龄必须在 0~150 之间");
        }
        System.out.println("年龄合法: " + age);



    }

    public static void main(String[] args) {
        validateAge(25);   // 正常
        // validateAge(200);  // 抛出 IllegalArgumentException



        try {
            readFile("test.txt");  // 调用者必须处理
        } catch (IOException e) {
            System.out.println("文件读取失败: " + e.getMessage());
        }

    }

    public static void readFile(String fileName) throws IOException {  // 必须声明或捕获
        FileReader fr = new FileReader(fileName);
    }
}