//package com.example.bookmanager.actuator;
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class ExternalApiHealthIndicator implements HealthIndicator {
//
//    private final RestTemplate restTemplate;
//
//    public ExternalApiHealthIndicator(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Override
//    public Health health() {
//
//        try {
//            // 调用外部API的健康检查接口
//            String url = "https://api.example.com/health";
//            String response = restTemplate.getForObject(url, String.class);
//
//            return Health.up()
//                    .withDetail("service", "external-api")
//                    .withDetail("response", response)
//                    .build();
//
//        } catch (Exception e) {
//            return Health.down()
//                    .withDetail("service", "external-api")
//                    .withDetail("error", e.getMessage())
//                    .build();
//        }
//    }
//}
