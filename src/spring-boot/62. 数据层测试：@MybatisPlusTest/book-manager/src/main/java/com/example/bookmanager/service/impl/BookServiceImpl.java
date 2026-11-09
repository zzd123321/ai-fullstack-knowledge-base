package com.example.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bookmanager.async.AsyncLogService;
import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.metric.BookMetrics;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.BookStatistics;
import com.example.bookmanager.service.BookService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import net.sf.jsqlparser.schema.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private AsyncLogService asyncLogService;

    @Autowired
    private BookMetrics bookMetrics;

    private static final Logger logger = LoggerFactory.getLogger(AsyncLogService.class);

    @Override
    @Caching(
            put = @CachePut(value = "books", key = "#result.id"),
            evict = @CacheEvict(value = "books", key = "'all'")
    )

    @Counted(value="books.created.total", description = "图书创建总次数")
    public Book createBook(Book book) {
        // 1. 业务校验
        if (book.getPrice() == null || book.getPrice() < 0) {
            throw new BusinessException("价格不能为负数");
        }
        if (book.getStock() == null || book.getStock() < 0) {
            throw new BusinessException("库存不能为负数");
        }
        // 2. 设置默认值（销量）
        book.setSales(0);

        // 3. 插入数据（主键自动回填）
        int rows = bookMapper.insert(book);
        if (rows != 1) {
//            asyncLogService.logOperation("新增图书失败");

            // 记录失败指标
//            bookMetrics.recordFailure();
            throw new BusinessException("新增图书失败");
        }

//        asyncLogService.logOperation("新增图书成功");

        // 记录成功指标
//        bookMetrics.recordSuccess();

        return book;
    }

    @Override
    @Cacheable(value = "books",
            key = "#id",
            sync = true
    )

    @Timed(value = "books.query.time", description = "图书查询耗时")
    public Book getBookById(Long id) {
        System.out.println("从数据库中查询图书：" + id);

        Book book = bookMapper.selectById(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book", id);
        }
        return book;
    }

    @Override
    @Cacheable(value = "books", key = "'all'",
            unless = "#result == null || #result.isEmpty()")
    public List<Book> getAllBooks() {
        System.out.println("从数据库中查询所有图书");

        return bookMapper.selectList(null);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "books", key = "#book.id"),
                    @CacheEvict(value = "books", key = "'all'")
            }
    )
    public Book updateBook(Book book) {
        // 1. 校验 ID
        if (book.getId() == null) {
            throw new BusinessException("更新时 ID 不能为空");
        }

        // 2. 检查记录是否存在
        Book existing = bookMapper.selectById(book.getId());
        if (existing == null) {
            throw new ResourceNotFoundException("要更新的图书不存在，ID: " + book.getId());
        }

        // 3. 执行更新（注意：MP 的 updateById 会忽略 null 字段，除非配置了更新策略）
        //    这里直接使用 updateById，它会根据实体中非 null 的字段进行更新
        int rows = bookMapper.updateById(book);
        if (rows != 1) {
            throw new BusinessException("更新图书失败");
        }

        // 延迟双删（异步）
        evictWithDelay(book.getId());

        return book;
    }

    private void evictWithDelay(Long id) {
        new Thread(() -> {
            try {
                Thread.sleep(200);
                cacheManager.getCache("books").evict(id);
                cacheManager.getCache("books").evict("'all'");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "books", key = "#id"),
                    @CacheEvict(value = "books", key = "'all'")
            }
    )
    public boolean deleteBook(Long id) {
        // 1. 校验 ID
        if (id == null) {
            throw new BusinessException("删除时 ID 不能为空");
        }

        // 2. 检查记录是否存在（可选，MP 的 deleteById 不会抛异常，但我们可以提前校验）
        Book existing = bookMapper.selectById(id);
        if (existing == null) {
            throw new ResourceNotFoundException("要删除的图书不存在，ID: " + id);
        }

        // 3. 逻辑删除（如果配置了 @TableLogic，则执行 UPDATE 设置 deleted = 1）
        int rows = bookMapper.deleteById(id);
        return rows > 0;
    }



    @Override
    public List<Book> searchBooks(String author, String category, Double minPrice, Double maxPrice) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();

        // 使用 Lambda 表达式引用字段
        wrapper.like(StringUtils.hasText(author), Book::getAuthor, author)
                .eq(StringUtils.hasText(category), Book::getCategory, category)
                .ge(minPrice != null, Book::getPrice, minPrice)
                .le(maxPrice != null, Book::getPrice, maxPrice)
                .orderByAsc(Book::getId);

        return bookMapper.selectList(wrapper);
    }

    @Override
    public List<Book> complexSearch(String keyword, String category, Double minPrice, Double maxPrice) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();

        // 组合条件：标题或作者包含关键字
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Book::getTitle, keyword)
                    .or()
                    .like(Book::getAuthor, keyword));
        }

        // 分类精确匹配
        wrapper.eq(StringUtils.hasText(category), Book::getCategory, category);

        // 价格区间
        wrapper.ge(minPrice != null, Book::getPrice, minPrice)
                .le(maxPrice != null, Book::getPrice, maxPrice);

        // 只查询库存 > 0 的图书
        wrapper.gt(Book::getStock, 0);

        // 按价格降序，价格相同按 ID 升序
        wrapper.orderByDesc(Book::getPrice)
                .orderByAsc(Book::getId);

        return bookMapper.selectList(wrapper);
    }

    @Override
    public Book findOneByTitle(String title) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getTitle, title);
        return bookMapper.selectOne(wrapper);
    }

    @Override
    public long countByCategory(String category) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getCategory, category);
        return bookMapper.selectCount(wrapper);
    }

    @Override
    public int updatePriceByCategory(String category, Double multiplier) {
        LambdaUpdateWrapper<Book> wrapper = new LambdaUpdateWrapper<>();

        // 更新条件
        wrapper.eq(Book::getCategory, category);

        // 设置更新字段
        wrapper.set(Book::getPrice, new Column("price * " + multiplier));
        // 或直接设置固定值
        // wrapper.set(Book::getCategory, "新技术");

        return bookMapper.update(null, wrapper);
    }

    @Override
    public Page<Book> getBooksByPage(int pageNum, int pageSize, String author, String category) {
        // 1. 创建 Page 对象
        Page<Book> page = new Page<>(pageNum, pageSize);

        // 2. 构建条件
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(author), Book::getAuthor, author)
                .eq(StringUtils.hasText(category), Book::getCategory, category)
                .orderByDesc(Book::getCreatedAt);

        // 3. 执行分页查询（MyBatis-Plus 自动注入 LIMIT 和 COUNT）
        return bookMapper.selectPage(page, wrapper);
    }

    @Override
    public BookStatistics getStatistics() {
        logger.info("开始统计图书信息，线程：{}", Thread.currentThread().getName());

        // 模拟复杂聚合计算耗时操作
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 1. 查询所有图书
        List<Book> books = bookMapper.selectList(null);

        // 2. 计算总数
        long totalBooks = books.size();

        // 3. 计算总库存
        long totalStock = books.stream()
                .mapToLong(book -> book.getStock() == null ? 0 : book.getStock())
                .sum();

        // 4. 计算平均价格
        double averagePrice = books.stream()
                .filter(book -> book.getPrice() != null)
                .mapToDouble(Book::getPrice)
                .average()
                .orElse(0.0);

        // 5. 按分类统计
        Map<String, Long> categoryCount = new LinkedHashMap<>();
        // 获取所有不重复的分类
        Set<String> categories = books.stream()
                .map(book -> book.getCategory() == null ? "未分类" : book.getCategory())
                .collect(Collectors.toSet());
        // 为每一个分类添加数量
        for (String category : categories) {
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Book::getCategory, category);
            Long count = bookMapper.selectCount(wrapper);
            categoryCount.put(category, count);
        }

        String generatedAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        logger.info("统计完成，图书总数：{}，总库存：{}，线程：{}",
                totalBooks, totalStock, Thread.currentThread().getName());

        return new BookStatistics(totalBooks, totalStock, averagePrice, categoryCount, generatedAt);
    }

    @Override
    public long count() {
        return bookMapper.selectCount(null);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {

        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getAuthor, author);
        return bookMapper.selectList(wrapper);
    }
}