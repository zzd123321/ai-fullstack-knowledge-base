package com.example.bookmanager.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class BookMetrics {

    private final Counter bookCreatedSuccess;
    private final Counter bookCreatedFailure;

    public BookMetrics(MeterRegistry registry) {
        this.bookCreatedSuccess = Counter.builder("books.created")
                .tag("status", "success")
                .description("图书创建成功次数")
                .register(registry);

        this.bookCreatedFailure = Counter.builder("books.created")
                .tag("status", "failure")
                .description("图书创建失败次数")
                .register(registry);
    }

    public void recordSuccess() {
        bookCreatedSuccess.increment();
    }

    public void recordFailure() {
        bookCreatedFailure.increment();
    }
}
