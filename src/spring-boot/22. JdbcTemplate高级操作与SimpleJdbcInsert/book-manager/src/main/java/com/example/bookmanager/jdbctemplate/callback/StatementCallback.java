package com.example.bookmanager.jdbctemplate.callback;

import java.sql.SQLException;
import java.sql.Statement;

public interface StatementCallback {

    Object handleStatement(Statement stmt) throws SQLException;
}
