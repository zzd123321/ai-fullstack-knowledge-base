package com.example.bookmanager.repository;

import com.example.bookmanager.model.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert orderInsert;

    @PostConstruct
    public void init() {
        this.orderInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id")
                .usingColumns("book_id", "quantity", "status");
    }

    public Order save(Order order) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        Number key = orderInsert.executeAndReturnKey(parameterSource);
        order.setId(key.longValue());
        return order;
    }

}
