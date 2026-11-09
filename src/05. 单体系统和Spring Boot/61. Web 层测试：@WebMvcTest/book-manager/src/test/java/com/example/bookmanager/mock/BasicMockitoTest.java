package com.example.bookmanager.mock;

import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasicMockitoTest {

    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("when-thenReturn示例")
    void testWhenThenReturn() {
        // 预设值（Stub）：模拟一个Book对象
        Book book = new Book("Spring Boot", "TianminZheng", 79.0, 10);
        when(bookMapper.selectById(1L)).thenReturn(book);

        // 使用方法调用
        Book result = bookMapper.selectById(1L);

        // 验证结果
        assertEquals("Spring Boot", result.getTitle());

        // 验证调用过程
        verify(bookMapper).selectById(1L);
    }

    @Test
    @DisplayName("when-thenThrow示例")
    void testWhenThenThrow() {

        // 预设抛出异常
        when(bookMapper.selectById(999L))
                .thenThrow(new RuntimeException("图书不存在"));

        // 验证异常
        assertThrows(RuntimeException.class, () -> bookMapper.selectById(999L));
    }

    @Test
    @DisplayName("连续调用返回不同值")
    void testConsecutiveReturns() {
        when(bookMapper.count())
                .thenReturn(5L)
                .thenReturn(10L)
                .thenReturn(15L);

        assertEquals(5L, bookMapper.count());
        assertEquals(10L, bookMapper.count());
        assertEquals(15L, bookMapper.count());
    }

}
