package com.example.bookmanager.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ChunkLoggingListener implements ChunkListener {

    private static final Logger logger = LoggerFactory.getLogger(ChunkLoggingListener.class);

    private long chunkStartTime;

    @Override
    public void beforeChunk(ChunkContext context) {
        chunkStartTime = System.currentTimeMillis();
        logger.debug("[Chunk 开始] {}", context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        long duration = System.currentTimeMillis() - chunkStartTime;
        logger.debug("[Chunk 完成] {} 耗时：{}ms", context.getStepContext().getStepName(), duration);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        logger.debug("[Chunk 失败] {}", context.getStepContext().getStepName());
    }
}
