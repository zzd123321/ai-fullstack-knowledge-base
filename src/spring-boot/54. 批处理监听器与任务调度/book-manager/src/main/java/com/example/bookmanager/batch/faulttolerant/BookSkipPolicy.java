package com.example.bookmanager.batch.faulttolerant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class BookSkipPolicy implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger(BookSkipPolicy.class);

    private static final int MAX_SKIP = 10;

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {

        logger.warn("跳过第{}条数据，原因：{}", skipCount + 1, t.getMessage());

        // 超过最大skip数，直接抛出异常
        if(skipCount >= MAX_SKIP) {
            throw new SkipLimitExceededException(MAX_SKIP, t);
        }

        // 数据格式错误，跳过
        if (t instanceof NumberFormatException) {
            return true;
        }

        // 数据校验失败，跳过
        if (t instanceof IllegalArgumentException) {
            return true;
        }

        // 其他异常，不跳过
        return false;
    }
}
