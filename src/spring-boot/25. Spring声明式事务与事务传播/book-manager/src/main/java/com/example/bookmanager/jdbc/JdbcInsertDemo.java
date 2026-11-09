package com.example.bookmanager.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcInsertDemo {
    public static void main(String[] args) {

        String sql = "insert into book(title,author,price,stock) values(?,?,?,?)";

        // 使用try-with-resource语法实现资源管理
        try(Connection conn = DriverManager.getConnection(
                "jdbc:h2:~/bookdb;DB_CLOSE_DELAY=-1", "sa", "");
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Spring 图书");
            pstmt.setString(2, "郑天民");
            pstmt.setDouble(3, 99.00);
            pstmt.setInt(4, 2);

            // 执行插入
            int row = pstmt.executeUpdate();
            System.out.println("成功插入：" + row);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
