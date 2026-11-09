package com.example.bookmanager.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUpdateDemo {

    public static void main(String[] args) {
        // 更新操作
        String updateSql = "UPDATE book SET price = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:h2:~/bookdb;DB_CLOSE_DELAY=-1", "sa", "");
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

            pstmt.setDouble(1, 89.00);
            pstmt.setLong(2, 1L);
            int rows = pstmt.executeUpdate();
            System.out.println("更新了 " + rows + " 条记录");

        } catch (SQLException e) {
            System.err.println("更新失败: " + e.getMessage());
        }

        // 删除操作
        String deleteSql = "DELETE FROM book WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:h2:~/bookdb;DB_CLOSE_DELAY=-1", "sa", "");
             PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {

            pstmt.setLong(1, 3L);
            int rows = pstmt.executeUpdate();
            System.out.println("删除了 " + rows + " 条记录");

        } catch (SQLException e) {
            System.err.println("删除失败: " + e.getMessage());
        }
    }
}