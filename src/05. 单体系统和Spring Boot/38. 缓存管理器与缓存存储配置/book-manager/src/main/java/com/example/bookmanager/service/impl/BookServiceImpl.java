package com.example.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import net.sf.jsqlparser.schema.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
//    @CachePut(value = "books", key = "#book.id")
    @Caching(
            put = @CachePut(value = "books", key = "#result.id"),
            evict = @CacheEvict(value = "books", key = "'all'")
    )
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
            throw new BusinessException("新增图书失败");
        }
        return book;
    }

    @Override
    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id) {
        System.out.println("从数据库中查询图书：" + id);

        Book book = bookMapper.selectById(id);
        if (book == null) {
            throw new ResourceNotFoundException("Book", id);
        }
        return book;
    }

    @Override
    @Cacheable(value = "books", key = "'all'")
    public List<Book> getAllBooks() {
        System.out.println("从数据库中查询所有图书");

        return bookMapper.selectList(null);
    }

    @Override
//    @CachePut(value = "books", key = "#book.id")
    @Caching(
            put = @CachePut(value = "books", key = "#book.id"),
            evict = @CacheEvict(value = "books", key = "'all'")
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
        return book;
    }

    @Override
//    @CacheEvict(value = "books", key = "#id", allEntries = true, beforeInvocation = true)
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
}