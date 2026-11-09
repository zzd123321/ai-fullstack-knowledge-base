package com.example.bookmanager.intergration;

import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Result;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "app.auth.enabled=false")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper.delete(null);
    }

    @Test
    @DisplayName("创建图书")
    @Order(1)
    void testCreateBook() {

        Book newBook = new Book("Spring Boot 实战", "Craig Walls", 79.0, 10, "编程");

        ResponseEntity<Result> response =
                restTemplate.postForEntity("/api/books", newBook, Result.class);

        // 1. 验证HTTP交互过程的正确性
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(200);

        // 2. 验证数据库中数据的正确性
        List<Book> books = bookMapper.selectList(null);
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Spring Boot 实战");
    }

    @Test
    @DisplayName("查询图书")
    @Order(2)
    void testGetBook() {
        Book book = new Book("Java 核心技术", "Cay Horstmann", 89.0, 8, "编程");
        bookMapper.insert(book);

        ResponseEntity<Result> response =
                restTemplate.getForEntity("/api/books/" + book.getId(), Result.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        // 解析data字段获取图书信息
        var data = (Map<?,?>)response.getBody().getData();
        assertThat(data.get("title")).isEqualTo("Java 核心技术");
        assertThat(data.get("author")).isEqualTo("Cay Horstmann");

    }

    @Test
    void testGetBookNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/books/999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    @DisplayName("更新图书 - PUT /api/books/{id}")
    void testUpdateBook() {

        Book book = new Book("测试书", "测试作者", 50.0, 5, "测试");
        bookMapper.insert(book);

        Book updateBook = new Book("测试书（第2版）", "测试作者", 60.0, 10, "测试");

        HttpEntity<Book> request = new HttpEntity<>(updateBook);
        ResponseEntity<Result> response = restTemplate.exchange(
                "/api/books/" + book.getId(),
                HttpMethod.PUT,
                request,
                Result.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book updated = bookMapper.selectById(book.getId());
        assertThat(updated.getTitle()).isEqualTo("测试书（第2版）");
        assertThat(updated.getPrice()).isEqualTo(60.0);

    }

    @Test
    @DisplayName("删除图书")
    void testDeleteBook() {
        Book book = new Book("待删除", "作者", 30.0, 3, "测试");
        bookMapper.insert(book);

        ResponseEntity<Result> response = restTemplate.exchange(
                "/api/books/" + book.getId(),
                HttpMethod.DELETE,
                null,
                Result.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Book deleted = bookMapper.selectById(book.getId());
        assertThat(deleted).isNull();

    }

    @Test
    @DisplayName("完整流程：创建 → 查询 → 更新 → 查询 → 删除 → 查询")
    void testFullLifecycle() {
        // 1. 创建
        Book newBook = new Book("完整流程测试", "作者", 100.0, 5, "测试");
        ResponseEntity<Result> createResponse = restTemplate.postForEntity(
                "/api/books", newBook, Result.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 从数据库获取 ID
        List<Book> books = bookMapper.selectList(null);
        Long bookId = books.get(0).getId();

        // 2. 查询
        ResponseEntity<Result> getResponse = restTemplate.getForEntity(
                "/api/books/" + bookId, Result.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 3. 更新
        Book updateBook = new Book("完整流程测试（更新）", "作者", 150.0, 10, "测试");
        HttpEntity<Book> updateRequest = new HttpEntity<>(updateBook);
        ResponseEntity<Result> updateResponse = restTemplate.exchange(
                "/api/books/" + bookId, HttpMethod.PUT, updateRequest, Result.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 4. 再次查询，验证更新
        Book updated = bookMapper.selectById(bookId);
        assertThat(updated.getTitle()).isEqualTo("完整流程测试（更新）");
        assertThat(updated.getPrice()).isEqualTo(150.0);

        // 5. 删除
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/books/" + bookId, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 6. 查询已删除的图书
        ResponseEntity<String> afterDeleteResponse = restTemplate.getForEntity(
                "/api/books/" + bookId, String.class);
        assertThat(afterDeleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("参数校验 - 无效请求体返回 400")
    void testCreateBookWithInvalidBody() {
        // 价格为负数
        Book invalidBook = new Book("测试", "作者", -10.0, 5, "测试");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/books", invalidBook, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(bookMapper.selectCount(null)).isEqualTo(0);
    }

    @Test
    @DisplayName("查询所有图书 - 返回列表")
    void testGetAllBooks() {
        // Given
        bookMapper.insert(new Book("书1", "作者1", 10.0, 1, "测试"));
        bookMapper.insert(new Book("书2", "作者2", 20.0, 2, "测试"));
        bookMapper.insert(new Book("书3", "作者3", 30.0, 3, "测试"));

        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/books", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("书1");
        assertThat(response.getBody()).contains("书2");
        assertThat(response.getBody()).contains("书3");
    }
}
