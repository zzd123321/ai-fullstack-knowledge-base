package com.example.bookmanager.jdbctemplate.callback;

import java.sql.*;

public class CallbackJdbcTemplate {

    public Object execute(StatementCallback callback) {

        // Try-With-Resource方法
        try(Connection conn = DriverManager.getConnection(
                "jdbc:h2:~/bookdb;DB_CLOSE_DELAY=-1", "sa", "");
            Statement stmt = conn.createStatement()) {

            return callback.handleStatement(stmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
