package com.example.bookmanager.service.impl;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BookManager Team
 * @since 2026-08-31
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
