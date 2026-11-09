package com.example.bookmanager.repository;

import com.example.bookmanager.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookRowMapper bookRowMapper;

    public Book findById(Long id) {
        String sql = "select * from book where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, bookRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Book> findAll() {
        String sql = "select * from book order by id desc";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    public List<Book> findByAuthor(String author) {
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at " +
                "FROM book WHERE author = ?";
        return jdbcTemplate.query(sql, bookRowMapper, author);
    }

    public List<Book> findByPriceRange(Double min, Double max) {
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at " +
                "FROM book WHERE price BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, bookRowMapper, min, max);
    }

    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Book save(Book book) {

        String sql = "INSERT INTO book (title, author, price, stock, category) " +
                "VALUES (?, ?, ?, ?, ?)";

        // 生成保存了主键的一个KeyHolder:自动生成主键
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); //new String[]{"id"}
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getStock());
            ps.setString(5, book.getCategory());
            return ps;
        }, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        Long id = (Long) keys.get("ID");
        book.setId(id);

        // 方案2
        //new String[]{"id"}
//        if (keyHolder.getKey() != null) {
//            book.setId(keyHolder.getKey().longValue());
//        }

        // 插入后重新查询，获取完整信息，包含自动生成的时间字段
        return findById(book.getId());
    }

    public Book update(Book book) {

        String sql = "UPDATE book SET title = ?, author = ?, price = ?, stock = ?, category = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStock(),
                book.getCategory(),
                book.getId());

        return findById(book.getId());
    }

    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        int rows = jdbcTemplate.update(sql,id);
        return rows > 0;
    }
}