package com.example.bookmanager.jdbc;

import java.sql.*;

public class JdbcQueryDemo {

    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 加载H2数据库的驱动
            Class.forName("org.h2.Driver");

            // 创建链接
            String url = "jdbc:h2:~/bookdb;DB_CLOSE_DELAY=-1";
            String user = "sa";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);

            // 创建Statement
            stmt = conn.createStatement();

            // 执行查询
            String sql = "select * from BOOK ";
            rs = stmt.executeQuery(sql);

            // 处理结果集
            System.out.println("图书列表");
            while (rs.next()) {
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                Double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                System.out.printf("ID: %d, 书名：%s, 作者：%s 价格：%.2f 库存：%d %n",
                    id, title, author, price, stock);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放资源
            try{
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
