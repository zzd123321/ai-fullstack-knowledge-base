package com.example.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@Order(1)
public class FeignTraceInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("进入FeignTraceInterceptor");

        String traceId = MDC.get(TraceIdFilter.TRACE_ID);
        if(StringUtils.hasText(traceId)) {
            requestTemplate.header(TraceIdFilter.TRACE_ID_HEADER, traceId);
        }
    }
}
