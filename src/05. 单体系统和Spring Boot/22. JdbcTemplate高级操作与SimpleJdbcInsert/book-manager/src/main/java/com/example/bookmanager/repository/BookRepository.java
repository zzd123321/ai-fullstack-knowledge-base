package com.example.bookmanager.repository;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.PageResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookRowMapper bookRowMapper;

    private SimpleJdbcInsert bookInsert;

    @PostConstruct
    public void init() {
        this.bookInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("book")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "author", "price", "stock", "category");
    }

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


    // 批量操作
    public void batchInsert(List<Book> books) {
        String sql = "INSERT INTO book (title, author, price, stock, category) VALUES (?, ?, ?, ?, ?)";

        // 只有一次网络请求
        jdbcTemplate.batchUpdate(sql, books, books.size(), (ps, book) -> {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getStock());
            ps.setString(5, book.getCategory());
        });
    }

    public void batchUpdate(List<Book> books) {
        String sql = "UPDATE book SET price = ?, stock = ? WHERE id = ?";
        jdbcTemplate.batchUpdate(sql, books, books.size(), (ps, book) -> {
            ps.setDouble(1, book.getPrice());
            ps.setInt(2, book.getStock());
            ps.setLong(3, book.getId());
        });
    }

    public void batchDeleteByIds(List<Long> ids) {
        String sql = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.batchUpdate(sql, ids, ids.size(), (ps, id) -> {
            ps.setLong(1, id);
        });
    }

    // 分页操作
    public List<Book> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        // H2/MySQL的分页方法
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at "
                + "FROM book ORDER BY id LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, bookRowMapper, size, offset);
    }

    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public PageResult<Book> findBooksWithPage(int page, int size, String sortBy, String direction) {
        // 构建排序条件
        String orderClause = "ORDER BY " + sortBy + " " + direction;

        // 分页查询
        int offset = (page - 1) * size;
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at "
                + "FROM book " +  orderClause + " LIMIT ? OFFSET ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, size, offset);

        // 查询总条数
        int total = countBooks();

        return new PageResult<>(page, size, total, books);
    }

    // SimpleJdbcInsert
    public Book saveWithSimpleJdbcInsert(Book book) {
        // 准备参数，用Map的形式
        Map<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("price", book.getPrice());
        params.put("stock", book.getStock());
        params.put("category", book.getCategory());

        // 执行插入，获取自动生成的主键
        Number key = bookInsert.executeAndReturnKey(params);
        book.setId(key.longValue());

        return findById(book.getId());
    }

    public Book saveWithBeanSource(Book book) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(book);
        Number key = bookInsert.executeAndReturnKey(parameterSource);
        book.setId(key.longValue());

        return findById(book.getId());
    }
}