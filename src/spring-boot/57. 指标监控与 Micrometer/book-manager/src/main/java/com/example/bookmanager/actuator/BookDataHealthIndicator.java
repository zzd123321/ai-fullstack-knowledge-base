package com.example.bookmanager.actuator;

import com.example.bookmanager.service.BookService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class BookDataHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    public BookDataHealthIndicator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public Health health() {

        try {
            // 图书业务逻辑的定制化校验
            long count = bookService.count();
            if(count >= 1) {
                return Health.up()
                        .withDetail("bookCount", count)
                        .withDetail("message", "图书数据正常")
                        .build();
            } else {
                return Health.down()
                        .withDetail("bookCount", count)
                        .withDetail("message", "图书数据为空，可能初始化失败")
                        .status(new Status("DEGRADE", "图书服务降级"))
                        .build();
            }

        } catch (Exception e) {
            return Health.down()
                    .withDetail("message", "图书数据检查失败")
                    .build();
        }
    }
}
