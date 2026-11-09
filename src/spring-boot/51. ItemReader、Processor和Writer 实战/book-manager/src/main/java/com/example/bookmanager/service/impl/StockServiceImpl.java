package com.example.bookmanager.service.impl;

import com.example.bookmanager.async.AsyncLogService;
import com.example.bookmanager.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncLogService.class);

    @Override
    public void syncStock() {
        logger.info("同步库存逻辑");
        // 实际同步逻辑
    }
}
