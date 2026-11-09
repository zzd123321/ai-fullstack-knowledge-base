package com.example.bookmanager.batch.listener;

import com.example.bookmanager.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class ItemLoggingListener implements ItemReadListener<Book>,
        ItemWriteListener<Book> ,
        ItemProcessListener<Book, Book> {

    private static final Logger logger = LoggerFactory.getLogger(ItemLoggingListener.class);

    // ========== 读取监听 ==========

    @Override
    public void beforeRead() {
        // 每条数据读取前执行（高频，建议只在 DEBUG 级别记录）
    }

    @Override
    public void afterRead(Book item) {
        logger.debug("[读取] {}", item.getTitle());
    }

    @Override
    public void onReadError(Exception ex) {
        logger.error("[读取异常] {}", ex.getMessage());
    }

    // ========== 处理监听 ==========

    @Override
    public void beforeProcess(Book item) {
        // 处理前
    }

    @Override
    public void afterProcess(Book item, Book result) {
        if (result == null) {
            logger.debug("[处理过滤] {}", item.getTitle());
        } else {
            logger.debug("[处理成功] {}", result.getTitle());
        }
    }

    @Override
    public void onProcessError(Book item, Exception e) {
        logger.error("[处理异常] {} - {}", item.getTitle(), e.getMessage());
    }

    // ========== 写入监听 ==========

    @Override
    public void beforeWrite(Chunk<? extends Book> items) {
        logger.debug("[写入开始] {} 条", items.size());
    }

    @Override
    public void afterWrite(Chunk<? extends Book> items) {
        logger.info("[写入完成] {} 条", items.size());
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends Book> items) {
        logger.error("[写入异常] {} 条失败: {}", items.size(), exception.getMessage());
    }
}
