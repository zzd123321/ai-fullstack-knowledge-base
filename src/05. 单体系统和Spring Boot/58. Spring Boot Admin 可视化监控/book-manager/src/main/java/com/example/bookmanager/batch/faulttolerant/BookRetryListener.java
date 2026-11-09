package com.example.bookmanager.batch.faulttolerant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

public class BookRetryListener implements RetryListener {

    private static final Logger logger = LoggerFactory.getLogger(BookSkipListener.class);

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {

        return true; // 返回true代表允许重试
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {

        if(throwable != null) {
            logger.error("重试结束，仍然失败：{}", throwable.getMessage());
        }
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        logger.error("重试第{}次，异常:{}", context.getRetryCount(), throwable.getMessage());
    }
}
