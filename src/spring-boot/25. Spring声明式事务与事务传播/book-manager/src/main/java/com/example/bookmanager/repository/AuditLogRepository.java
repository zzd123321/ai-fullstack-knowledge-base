package com.example.bookmanager.repository;

import com.example.bookmanager.model.AuditLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

@Repository
public class AuditLogRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuditLogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AuditLog save(AuditLog log) {
        String sql = "INSERT INTO audit_log (message, created_at) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, log.getMessage());
            ps.setObject(2, log.getCreatedAt());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        Long id = (Long) keys.get("ID");
        log.setId(id);

        return log;
    }
}