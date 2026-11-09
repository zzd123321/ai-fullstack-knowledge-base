package com.example.bookmanager.metric;

import com.example.bookmanager.mapper.BookMapper;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

@Component
public class BookStockMetrics implements MeterBinder {

    private final BookMapper bookMapper;

    public BookStockMetrics(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {

        // 当前图书总数量
        Gauge.builder("books.total.current", bookMapper,
                mapper -> mapper.selectCount(null))
                .description("当前图书总数量")
                .register(meterRegistry);

        // 当前图书总库存
        Gauge.builder("books.stock.total", bookMapper,
                mapper -> mapper.sumStock())
                .description("当前图书总库存")
                .register(meterRegistry);

    }
}
