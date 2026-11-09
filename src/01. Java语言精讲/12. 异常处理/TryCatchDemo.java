import java.io.*;

public class TryCatchDemo {

    public void readFile() throws FileNotFoundException {  // 必须声明或捕获
        FileReader fr = new FileReader("file.txt");
    }

    // 非检查异常：可以不处理（但建议处理）
    public void divide(int a, int b) {
        int result = a / b;  // 若 b=0，抛出 ArithmeticException（运行时异常）
    }



    public static void main(String[] args) {

        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]);  // 数组索引越界
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("捕获到异常: " + e.getMessage());
        } finally {
            System.out.println("finally 块总是执行");
        }
        System.out.println("程序继续执行");



        try {
            int[] arr = null;
            System.out.println(arr.length);   // NullPointerException
            int result = 10 / 0;              // ArithmeticException（如果前句不抛）
        } catch (NullPointerException e) {
            System.out.println("空指针异常: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("算术异常: " + e.getMessage());
        }
        System.out.println("程序结束");
    }


    public static void readFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // reader 自动关闭

        try (FileInputStream fis = new FileInputStream("in.txt");
             FileOutputStream fos = new FileOutputStream("out.txt")) {
            // 使用两个流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
