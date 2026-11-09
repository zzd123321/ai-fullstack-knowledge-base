package com.example.bookmanager.intergration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
@ActiveProfiles("test")
public class BookApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetBook() {
        // 发起GET请求
        ResponseEntity<String> response
                = restTemplate.getForEntity("/api/books/1", String.class);

        // 断言
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void testGetBookWithHeader() {

        // 构建消息头
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("token 123456");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // 发起GET请求
        ResponseEntity<String> response
                = restTemplate.exchange(
                        "/api/books/1",
                        HttpMethod.GET,
                        request,
                        String.class);

        // 断言
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
