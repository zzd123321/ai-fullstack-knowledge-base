package com.example.bookmanager.mock;

import com.example.bookmanager.exception.BusinessException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.service.BookService;
import com.example.bookmanager.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService单元测试")
public class BookServiceTest {

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

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
    }

    @Nested
    @DisplayName("createBook 方法测试")
    class CreateBookTests {

        @Test
        @DisplayName("正常创建图书")
        void testCreateBookSuccess() {

            // 假设调用bookMapper的insert方法始终成功
            // When
            Book newBook = new Book("Java核心技术", "Tianmin", 89.0, 8);
            when(bookMapper.insert(any(Book.class))).thenReturn(1);

            // 方法调用
            Book result = bookService.createBook(newBook);

            // 验证结果
            assertNotNull(result);
            assertEquals("Java核心技术", result.getTitle());
            assertEquals(0, result.getSales());

            // 验证BookMapper被调用
            verify(bookMapper, times(1)).insert(any(Book.class));
        }

        @Test
        @DisplayName("创建图书时，价格为负应该排除异常")
        void testCreateBookWithNegativePrice() {

            Book book = new Book("测试", "Tianmin", -10.0, 5);

            // When和Then
            BusinessException exception = assertThrows(
                    BusinessException.class,
                    () -> bookService.createBook(book)
            );
            assertTrue(exception.getMessage().contains("价格"));
            assertTrue(exception.getMessage().contains("负数"));

            // 验证Mapper没有被调用
            verify(bookMapper, never()).insert(any(Book.class));
        }

        @Test
        @DisplayName("创建图书时，价格为空应抛出异常")
        void testCreateBookWithNullPrice() {
            Book book = new Book("测试", "作者", null, 5);

            BusinessException exception = assertThrows(
                    BusinessException.class,
                    () -> bookService.createBook(book)
            );
            assertTrue(exception.getMessage().contains("价格"));

            verify(bookMapper, never()).insert(any(Book.class));
        }

        @Test
        @DisplayName("创建图书时，库存为负数应抛出异常")
        void testCreateBookWithNegativeStock() {
            Book book = new Book("测试", "作者", 50.0, -5);

            BusinessException exception = assertThrows(
                    BusinessException.class,
                    () -> bookService.createBook(book)
            );
            assertTrue(exception.getMessage().contains("库存"));

            verify(bookMapper, never()).insert(any(Book.class));
        }

        @Test
        @DisplayName("创建图书时，使用ArgumentCaptor验证参数")
        void testCreateBookWithArgumentCaptor() {

            // 假设调用bookMapper的insert方法始终成功
            // When
            Book newBook = new Book("Java核心技术", "Tianmin", 89.0, 8);
            when(bookMapper.insert(any(Book.class))).thenReturn(1);

            //Then
            bookService.createBook(newBook);

            // 通过ArgumentCaptor捕获参数
            ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
            verify(bookMapper).insert(captor.capture());

            Book capturedBook = captor.getValue();
            assertEquals("Java核心技术", capturedBook.getTitle());
            assertEquals("Tianmin", capturedBook.getAuthor());
            assertEquals(0, capturedBook.getSales());

        }
    }

    @Nested
    @DisplayName("getBookById方法测试")
    class GetBookByIdTests {

        @Test
        @DisplayName("查询存在的图书")
        void testGetBookByIdFound() {

            // When
            when(bookMapper.selectById(1L)).thenReturn(sampleBook);

            Book result = bookService.getBookById(1L);

            // Then
            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Spring Boot 实战", result.getTitle());

            verify(bookMapper, times(1)).selectById(1L);
        }

        @Test
        @DisplayName("查询不存在的图书应抛出异常")
        void testGetBookByIdNotFound() {

            when(bookMapper.selectById(999L)).thenReturn(null);

            ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> bookService.getBookById(999L)
            );
            assertTrue(exception.getMessage().contains("999"));

            verify(bookMapper, times(1)).selectById(999L);
        }
    }

    @Nested
    @DisplayName("getAllBooks 方法测试")
    class GetAllBooksTests {

        @Test
        @DisplayName("查询所有图书（有数据）")
        void testGetAllBooksWithData() {
            // Given
            List<Book> books = Arrays.asList(
                    sampleBook,
                    new Book("Java 核心技术", "Cay Horstmann", 89.0, 8)
            );
            when(bookMapper.selectList(null)).thenReturn(books);

            // When
            List<Book> result = bookService.getAllBooks();

            // Then
            assertEquals(2, result.size());
            assertEquals("Spring Boot 实战", result.get(0).getTitle());
            assertEquals("Java 核心技术", result.get(1).getTitle());

            verify(bookMapper, times(1)).selectList(null);
        }

        @Test
        @DisplayName("查询所有图书（无数据）")
        void testGetAllBooksEmpty() {
            // Given
            when(bookMapper.selectList(null)).thenReturn(Collections.emptyList());

            // When
            List<Book> result = bookService.getAllBooks();

            // Then
            assertTrue(result.isEmpty());

            verify(bookMapper, times(1)).selectList(null);
        }
    }

    @Nested
    @DisplayName("updateBook 方法测试")
    class UpdateBookTests {

        @Test
        @DisplayName("正常更新图书")
        void testUpdateBookSuccess() {

            Book updateBook = new Book("Spring Boot 实战（第2版）", "Craig Walls", 99.0, 15);
            updateBook.setId(1L);

            //When
            InOrder inOrder = inOrder(bookMapper);

            when(bookMapper.selectById(1L)).thenReturn(sampleBook);
            when(bookMapper.updateById(any(Book.class))).thenReturn(1); //模拟更新成功
            when(bookMapper.selectById(1L)).thenReturn(updateBook);

            Book result = bookService.updateBook(updateBook);

            // Then
            assertNotNull(result);
            assertEquals("Spring Boot 实战（第2版）", result.getTitle());
            assertEquals(99.0, result.getPrice());

            inOrder.verify(bookMapper).selectById(1L);
            inOrder.verify(bookMapper, times(1)).updateById(any(Book.class));
            inOrder.verify(bookMapper).selectById(1L);

            verifyNoMoreInteractions(bookMapper);  // 确保没有其他调用
        }

        @Test
        @DisplayName("更新时 ID 为空应抛出异常")
        void testUpdateBookWithNullId() {
            Book book = new Book("测试", "作者", 50.0, 5);
            // id 为 null

            BusinessException exception = assertThrows(
                    BusinessException.class,
                    () -> bookService.updateBook(book)
            );
            assertTrue(exception.getMessage().contains("ID"));

            verify(bookMapper, never()).updateById(any(Book.class));
        }

        @Test
        @DisplayName("更新不存在的图书应抛出异常")
        void testUpdateBookNotFound() {
            Book book = new Book("测试", "作者", 50.0, 5);
            book.setId(999L);

            when(bookMapper.selectById(999L)).thenReturn(null);

            ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> bookService.updateBook(book)
            );

            verify(bookMapper, never()).updateById(any(Book.class));
        }
    }

    @Nested
    @DisplayName("deleteBook 方法测试")
    class DeleteBookTests {

        @Test
        @DisplayName("正常删除图书")
        void testDeleteBookSuccess() {
            // Given
            when(bookMapper.selectById(1L)).thenReturn(sampleBook);
            when(bookMapper.deleteById(1L)).thenReturn(1);

            // When
            boolean result = bookService.deleteBook(1L);

            // Then
            assertTrue(result);
            verify(bookMapper, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("删除时 ID 为空应抛出异常")
        void testDeleteBookWithNullId() {
            BusinessException exception = assertThrows(
                    BusinessException.class,
                    () -> bookService.deleteBook(null)
            );
            assertTrue(exception.getMessage().contains("ID"));

            verify(bookMapper, never()).deleteById(anyLong());
        }

        @Test
        @DisplayName("删除不存在的图书应抛出异常")
        void testDeleteBookNotFound() {
            when(bookMapper.selectById(999L)).thenReturn(null);

            assertThrows(ResourceNotFoundException.class,
                    () -> bookService.deleteBook(999L));

            verify(bookMapper, never()).deleteById(999L);
        }
    }
}
