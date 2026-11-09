package com.example.bookmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Override
    public List<Book> searchBooks(String author, String category, Double minPrice, Double maxPrice) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(author), Book::getAuthor, author)
                .eq(StringUtils.hasText(category), Book::getCategory, category)
                .ge(minPrice != null, Book::getPrice, minPrice)
                .le(maxPrice != null, Book::getPrice, maxPrice)
                .orderByAsc(Book::getId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean updateBookWithVersion(Book book) {
        // 先查询当前版本的数据
        Book existing = baseMapper.selectById(book.getId());
        if( existing == null ) {
            return false;
        }

        // 设置版本号（用户实现乐观锁）
        book.setVersion(existing.getVersion());

        // 更新时，MP会自动添加基于version字段实现查询的条件
        int rows = baseMapper.updateById(book);
        return rows > 0; // 如果rows = 0表示数据库中版本已经更新了，所以更新会失败
    }
}