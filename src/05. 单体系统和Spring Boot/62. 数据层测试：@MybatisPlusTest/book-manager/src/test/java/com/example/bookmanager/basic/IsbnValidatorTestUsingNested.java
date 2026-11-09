package com.example.bookmanager.basic;

import com.example.bookmanager.utils.IsbnValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ISBN校验工具类测试")
public class IsbnValidatorTestUsingNested {

    @Nested
    @DisplayName("isValid方法测试")
    class IsValidTests {
        @Test
        @DisplayName("有效的ISBN-10：无连字符")
        void testValidISBN10() {
            assertTrue(IsbnValidator.isValid("0306406152"));
        }

        @Test
        @DisplayName("有效的ISBN-10：有连字符")
        void testValidISBN10WithHyphens() {
            assertTrue(IsbnValidator.isValid("0-306-40615-2"));
        }

        @Test
        @DisplayName("有效的ISBN-10：最后一位为x")
        void testValidISBN10WithX() {
            assertTrue(IsbnValidator.isValid("030640615x"));
        }

        @Test
        @DisplayName("有效的ISBN-13")
        void testValidISBN13() {
            assertTrue(IsbnValidator.isValid("9780306406157"));
        }

        @Test
        @DisplayName("有效的ISBN-13")
        void testValidISBN13With979() {
            assertTrue(IsbnValidator.isValid("9790306406157"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("null或空字符串，应该返回false")
        void testInvalidNullOrEmpty(String isbn) {
            assertFalse(IsbnValidator.isValid(isbn));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "12345",                    // 长度不足
                "12345678901234",           // 长度超出
                "abcdefghij",               // 非数字
                "030640615A",               // 最后一位不是数字或 X
                "978030640615",             // ISBN-13 长度不足
                "1234567890123",            // 不以 978/979 开头
                "978030640615A"             // ISBN-13 包含非数字
        })
        @DisplayName("无效的ISBN应该返回false")
        void testInvalidISBN(String isbn) {
            assertFalse(IsbnValidator.isValid(isbn));
        }
    }

    @Nested
    @DisplayName("getType方法测试")
    class GetTypeTests {
        @ParameterizedTest
        @CsvSource({
                "0306406152, ISBN-10",
                "0-306-40615-2, ISBN-10",
                "097522980X, ISBN-10",
                "9780306406157, ISBN-13",
                "9791234567890, ISBN-13"
        })
        @DisplayName("获取ISBN类型")
        void testGetType(String isbn, String expectedType) {
            assertEquals(expectedType, IsbnValidator.getType(isbn));
        }

        @Test
        @DisplayName("无效ISBN调用，获取getType失败，抛出异常")
        void testGetTypeWithException() {
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> IsbnValidator.getType(null)
            );
            assertTrue(exception.getMessage().contains("无效的 ISBN"));
        }
    }

    @Nested
    @DisplayName("normalize方法测试")
    class NormalizeTests {
        @ParameterizedTest
        @CsvSource({
                "0-306-40615-2, 0306406152",
                "978 0 306 40615 7, 9780306406157",
                "0306406152, 0306406152"
        })
        void testNormalize(String input, String expected) {
            assertEquals(expected, IsbnValidator.normalize(input));
        }

        @Test
        @DisplayName("normalize(null)应该抛出异常")
        void testNormalizeNull() {
            assertThrows(IllegalArgumentException.class,
                    () -> IsbnValidator.normalize(null));
        }
    }
}
