package homework2;

import java.io.*;

public class Homework2 {
    public static void methodA() throws IOException {
        throw new IOException("IO异常");
    }

    public static void methodB() throws Exception {
        try {
            methodA();
        } catch (IOException e) {
            System.out.println("在 methodB 中捕获: " + e.getMessage());
            throw new Exception("包装异常", e);
        }
    }

    public static void main(String[] args) {
        try {
            methodB();
        } catch (Exception e) {
            System.out.println("在 main 中捕获: " + e.getMessage());
            System.out.println("原因: " + e.getCause());
        }
    }
}