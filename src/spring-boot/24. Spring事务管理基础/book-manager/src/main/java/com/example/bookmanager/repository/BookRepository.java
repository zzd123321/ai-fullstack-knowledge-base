package com.example.bookmanager.repository;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.PageResult;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
        this.bookInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("book")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "author", "price", "stock", "category");
    }

    public Book findById(Long id) {
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at " +
                "FROM book WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, bookRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // 获取库存
    public int getStock(Long id) {
        String sql = "SELECT stock FROM book WHERE id = ?";
        Integer stock = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return stock == null ? 0 : stock;
    }

    // 扣减库存（返回影响行数）
    public int decreaseStock(Long id, int quantity) {
        String sql = "UPDATE book SET stock = stock - ? WHERE id = ? AND stock >= ?";
        return jdbcTemplate.update(sql, quantity, id, quantity);
    }

    // 增加销量
    public int increaseSales(Long id, int quantity) {
        String sql = "UPDATE book SET sales = sales + ? WHERE id = ?";
        return jdbcTemplate.update(sql, quantity, id);
    }


    public List<Book> findAll() {
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at " +
                "FROM book ORDER BY id";
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
        String sql = "INSERT INTO book (title, author, price, stock, category) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getStock());
            ps.setString(5, book.getCategory());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            book.setId(keyHolder.getKey().longValue());
        }
        // 插入后重新查询，获取完整信息（含自动生成的时间字段）
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
                book.getId()
        );

        // 重新查询，确保返回包含最新时间戳的完整对象
        return findById(book.getId());
    }


    public boolean deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        return affectedRows > 0;
    }

    // ---------- 删除 ----------
    public void batchInsert(List<Book> books) {
        String sql = "INSERT INTO book (title, author, price, stock, category) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, books, books.size(), (ps, book) -> {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setDouble(3, book.getPrice());
            ps.setInt(4, book.getStock());
            ps.setString(5, book.getCategory());
        });
    }

    public void batchUpdateBooks(List<Book> books) {
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

    // ---------- 分页查询 ----------
    public List<Book> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at " +
                "FROM book ORDER BY id LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, bookRowMapper, size, offset);
    }


    public int countBooks() {
        String sql = "SELECT COUNT(*) FROM book";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public PageResult<Book> findBooksWithPage(int page, int size, String sortBy, String direction) {
        // 1. 构建排序子句
        String orderClause = "ORDER BY " + sortBy + " " + direction;

        // 2. 分页查询
        int offset = (page - 1) * size;
        String sql = "SELECT id, title, author, price, stock, category, created_at, updated_at " +
                "FROM book " + orderClause + " LIMIT ? OFFSET ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, size, offset);

        // 3. 查询总数
        int total = countBooks();

        return new PageResult<>(page, size, total, books);
    }

    // ---------- SimpleJdbcInsert  ----------
    public Book saveWithSimpleJdbcInsert(Book book) {
        // 准备参数（Map 形式）
        Map<String, Object> params = new HashMap<>();
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("price", book.getPrice());
        params.put("stock", book.getStock());
        params.put("category", book.getCategory());

        // 执行插入，获取自动生成的主键
        Number key = bookInsert.executeAndReturnKey(params);
        book.setId(key.longValue());

        return book;
    }

    public Book saveWithBeanSource(Book book) {
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(book);
        Number key = bookInsert.executeAndReturnKey(paramSource);
        book.setId(key.longValue());
        return book;
    }
}