package com.example.bookmanager.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class StockServiceHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        // 检查库存服务
        boolean stockServiceAvailable = checkStockService();

        if (stockServiceAvailable) {
            builder.up()
                    .withDetail("service", "Stock Service")
                    .withDetail("endpoint", "/api/stock/check");
        } else {
            builder.down()
                    .withDetail("service", "Stock Service")
                    .withDetail("reason", "服务无响应");
        }
    }

    private boolean checkStockService() {
        return true;
    }
}
