package com.example.bookmanager.jdbctemplate.abstractclass;


import java.sql.*;

public abstract class AbstractJdbcTemplate {

    public final Object execute(String sql) {

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
            rs = stmt.executeQuery(sql);

            // 变化的部分：由子类实现
            return handleResultSet(rs);

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

    protected abstract Object handleResultSet(ResultSet rs) throws SQLException;

}
