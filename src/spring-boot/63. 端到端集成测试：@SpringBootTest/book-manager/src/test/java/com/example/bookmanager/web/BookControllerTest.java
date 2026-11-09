package com.example.bookmanager.web;

import com.example.bookmanager.controller.BookController;
import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.interceptor.AuthInterceptor;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@DisplayName("BookController Web 层测试")
//@ActiveProfiles("test")
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthInterceptor authInterceptor;

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Spring Boot 实战");
        sampleBook.setAuthor("Craig Walls");
        sampleBook.setPrice(79.0);
        sampleBook.setStock(10);
        sampleBook.setSales(0);

        // Mock拦截器，执行所有请求
        try {
            when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    @DisplayName("GET /api/books - 查询所有图书")
    class GetAllBookTests {

        @Test
        @DisplayName("返回所有图书，状态码200")
        void testGetAllBook() throws Exception {

            // Given
            List<Book> books = Arrays.asList(
                    sampleBook,
                    new Book("Java语法", "Tianmin", 89.0, 8)
            );
            when(bookService.getAllBooks()).thenReturn(books);

            // 执行Web请求
            mockMvc.perform(get("/api/books")
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data", hasSize(2)))
                    .andExpect(jsonPath("$.data[0].title").value("Spring Boot 实战"))
                    .andExpect(jsonPath("$.data[1].title").value("Java语法"));

            verify(bookService, times(1)).getAllBooks();
        }

        @Test
        @DisplayName("无图书时返回空数据")
        void testGetAllBooksEmpty() throws Exception {
            when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/books"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data", hasSize(0)));

            verify(bookService, times(1)).getAllBooks();
        }
    }

    @Nested
    @DisplayName("GET /api/books/{id} - 查询单本图书")
    class GetBookByIdTests {

        @Test
        @DisplayName("查询存在的图书，状态码 200")
        void testGetBookById() throws Exception {
            when(bookService.getBookById(1L)).thenReturn(sampleBook);

            mockMvc.perform(get("/api/books/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.data.title").value("Spring Boot 实战"))
                    .andExpect(jsonPath("$.data.author").value("Craig Walls"))
                    .andExpect(jsonPath("$.data.price").value(79.0));

            verify(bookService).getBookById(1L);
        }

        @Test
        @DisplayName("查询不存在的图书，返回 404")
        void testGetBookByIdNotFound() throws Exception {
            when(bookService.getBookById(999L))
                    .thenThrow(new ResourceNotFoundException("Book", 999L));

            mockMvc.perform(get("/api/books/{id}", 999L))
                    .andExpect(status().isNotFound());

            verify(bookService).getBookById(999L);
        }
    }

    @Nested
    @DisplayName("GET /api/books/author - 按作者查询")
    class GetBooksByAuthorTests {

        @Test
        @DisplayName("按作者查询返回匹配结果")
        void testGetBooksByAuthor() throws Exception {
            when(bookService.getBooksByAuthor("Craig Walls"))
                    .thenReturn(Collections.singletonList(sampleBook));

            mockMvc.perform(get("/api/books/author")
                            .param("author", "Craig Walls"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(1)))
                    .andExpect(jsonPath("$.data[0].author").value("Craig Walls"));

            verify(bookService).getBooksByAuthor("Craig Walls");
        }

        @Test
        @DisplayName("缺少 author 参数，返回 500")
        void testGetBooksByAuthorWithoutParam() throws Exception {
            mockMvc.perform(get("/api/books/author"))
                    .andExpect(status().isInternalServerError());

            verify(bookService, never()).getBooksByAuthor(any());
        }
    }

    @Nested
    @DisplayName("POST /api/books - 创建图书")
    class CreateBookTests {

        @Test
        @DisplayName("创建图书成功，返回200")
        void testCreateBookSuccess() throws Exception {
            Book newBook = new Book("Java核心技术", "Tianmin", 89.0, 8);
            when(bookService.createBook(any(Book.class))).thenReturn(sampleBook);

            mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(200))
                    .andExpect(jsonPath("$.data.id").value(1))
                    .andExpect(jsonPath("$.data.title").value("Spring Boot 实战"));

            verify(bookService, times(1)).createBook(any(Book.class));
        }

        @Test
        @DisplayName("价格为负，Service 抛出业务异常")
        void testCreateBookWithBusinessException() throws Exception {
            Book newBook = new Book("Java核心技术", "Tianmin", -89.0, 8);
            when(bookService.createBook(any(Book.class)))
                    .thenThrow(new BusinessException("价格不能为负数"));

            mockMvc.perform(post("/api/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newBook)))
                    .andExpect(status().isBadRequest());

        }
    }

    @Nested
    @DisplayName("PUT /api/books/{id} - 更新图书")
    class UpdateBookTests {

        @Test
        @DisplayName("更新图书成功")
        void testUpdateBookSuccess() throws Exception {
            Book updateBook = new Book("Spring Boot 实战（第2版）", "Craig Walls", 99.0, 15);
            updateBook.setId(1L);
            when(bookService.updateBook(any(Book.class))).thenReturn(updateBook);

            mockMvc.perform(put("/api/books/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateBook)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.title").value("Spring Boot 实战（第2版）"))
                    .andExpect(jsonPath("$.data.price").value(99.0));

            verify(bookService).updateBook(any(Book.class));
        }

        @Test
        @DisplayName("更新不存在的图书，返回 404")
        void testUpdateBookNotFound() throws Exception {
            Book updateBook = new Book("测试", "作者", 50.0, 5);
            updateBook.setId(999L);
            when(bookService.updateBook(any(Book.class)))
                    .thenThrow(new ResourceNotFoundException("Book", 999L));

            mockMvc.perform(put("/api/books/{id}", 999L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateBook)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE /api/books/{id} - 删除图书")
    class DeleteBookTests {

        @Test
        @DisplayName("删除图书成功，返回 204")
        void testDeleteBookSuccess() throws Exception {
            when(bookService.deleteBook(1L)).thenReturn(true);

            mockMvc.perform(delete("/api/books/{id}", 1L))
                    .andExpect(status().isOk());

            verify(bookService).deleteBook(1L);
        }

        @Test
        @DisplayName("删除不存在的图书，返回 404")
        void testDeleteBookNotFound() throws Exception {
            when(bookService.deleteBook(999L))
                    .thenThrow(new ResourceNotFoundException("Book", 999L));

            mockMvc.perform(delete("/api/books/{id}", 999L))
                    .andExpect(status().isNotFound());

            verify(bookService).deleteBook(999L);
        }
    }
}
