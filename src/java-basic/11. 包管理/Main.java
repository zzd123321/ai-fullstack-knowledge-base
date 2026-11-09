
import java.util.*;      // 导入 java.util 包中的所有类
import java.io.*;        // 导入 java.io 包中的所有类

import static java.lang.Math.PI;
import static java.lang.Math.pow;


public class Main {
    public static void main(String[] args) {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());


        List<String> list = new ArrayList<>();
        File file = new File("test.txt");


        // 直接使用 PI 和 pow，无需 Math.PI / Math.pow
        double area = PI * pow(5, 2);
        System.out.println(area);  // 78.53981633974483


    }
}
