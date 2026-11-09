package com.example.bookmanager.jdbctemplate.general;

import com.example.bookmanager.jdbctemplate.callback.StatementCallback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeneralJdbcTemplate {

    // 通用的查询方法
    public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
        return (List<T>)execute(stmt -> {
            ResultSet rs = stmt.executeQuery(sql);
            List<T> list = new ArrayList<>();
            int rowNum = 0;
            while (rs.next()) {
                T item = rowMapper.mapRow(rs, rowNum++);
                list.add(item);
            }
            return list;
        });
    }


    public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
        List<T> list = query(sql, rowMapper);
        if(list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public int update(String sql) {
        return (int)execute(stmt -> stmt.executeUpdate(sql));
    }

    private Object execute(StatementCallback callback) {

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
