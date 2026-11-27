package com.example.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "book.product")
@RefreshScope
@Data
public class BookProductProperties {

    private Integer pageSize = 10;
    private Boolean cacheEnabled = true;
}
