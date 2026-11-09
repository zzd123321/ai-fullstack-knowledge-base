package com.example.bookmanager.batch.faulttolerant;

import com.example.bookmanager.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookSkipListener implements SkipListener<Book, Book> {

    private static final Logger logger = LoggerFactory.getLogger(BookSkipListener.class);

    private final JdbcTemplate jdbcTemplate;

    public BookSkipListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onSkipInRead(Throwable t) {

        logger.warn("读取数据时跳过：{}", t.getMessage());
    }

    @Override
    public void onSkipInProcess(Book item, Throwable t) {

        logger.warn("处理数据时跳过：{}，原因：{}", item.getTitle(), t.getMessage());

        saveError(item, "处理失败：" + t.getMessage());
    }

    @Override
    public void onSkipInWrite(Book item, Throwable t) {

        logger.warn("写入数据时跳过：{}，原因：{}", item.getTitle(), t.getMessage());

        saveError(item, "写入失败：" + t.getMessage());
    }

    private void saveError(Book book, String reason) {
        try {
            String sql = "INSERT INTO book_import_error " +
                    "(title, author, price, stock, error_reason) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    book.getTitle(), book.getAuthor(),
                    book.getPrice(), book.getStock(), reason);
        } catch (Exception e) {
            logger.error("保存错误数据失败", e);
        }
    }
}
