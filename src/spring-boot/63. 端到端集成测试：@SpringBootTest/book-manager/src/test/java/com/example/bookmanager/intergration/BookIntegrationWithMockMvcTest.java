package com.example.bookmanager.intergration;

import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "app.auth.enabled=false")
@AutoConfigureMockMvc
public class BookIntegrationWithMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper.delete(null);
    }

    @Test
    @DisplayName("创建并查询图书")
    void testCreateAndGet() throws Exception {

        String bookJson = """
                {
                    "title": "Spring Boot 实战",
                    "author": "Craig Walls",
                    "price": 79.0,
                    "stock": 10,
                    "category": "编程"
                }
                """;

        // 创建图书
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isOk());

        // 查询图书
        List<Book> books = bookMapper.selectList(null);
        assertThat(books).hasSize(1);

        mockMvc.perform(get("/api/books/" + books.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Spring Boot 实战"));
    }
}
