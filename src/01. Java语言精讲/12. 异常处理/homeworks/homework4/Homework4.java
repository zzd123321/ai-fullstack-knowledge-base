package homework4;

import java.io.*;

public class Homework4 {
    public static void copyFile(String src, String dest) {
        // 使用 try-with-resources 自动管理资源
        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(dest)) {
            
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            System.out.println("文件复制成功: " + src + " -> " + dest);
            
        } catch (IOException e) {
            System.err.println("文件操作失败: " + e.getMessage());
            e.printStackTrace();
        }
        // in 和 out 自动关闭，无需手动处理
    }
}