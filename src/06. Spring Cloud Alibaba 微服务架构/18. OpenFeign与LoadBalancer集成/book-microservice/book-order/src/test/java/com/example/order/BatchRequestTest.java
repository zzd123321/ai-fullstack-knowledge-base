package com.example.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = BookOrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BatchRequestTest {

    @Autowired
    private TestRestTemplate restTemplate;// 注入测试用的RestTemplate

    @Test
    void batchRequest50Times() {

        String url = "http://localhost:8083/api/discovery/book/instance-port";

        for (int i = 0; i < 50; i++) {
            //模拟50次请求
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        }

    }
}
