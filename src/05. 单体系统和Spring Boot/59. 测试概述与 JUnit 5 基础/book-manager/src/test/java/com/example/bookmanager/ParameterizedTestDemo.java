package com.example.bookmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("参数化测试示例")
public class ParameterizedTestDemo {

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    @DisplayName("正数测试")
    void testPositive(int number) {
        assertTrue(number > 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Alice", "Bob", "Charlie"})
    @DisplayName("字符串长度测试")
    void testStringLength(String name) {
        assertTrue(name.length() > 2);
    }


    @ParameterizedTest
    @CsvSource({
            "1, 1, 2",
            "2, 3, 5",
            "10, 20, 30",
            "-1, 1, 0"
    })
    @DisplayName("加法计算")
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

    @ParameterizedTest
    @MethodSource("provideBookData")
    @DisplayName("图书数据测试")
    void testBookData(String title, double price) {
        assertNotNull(title);
        assertTrue(price > 0);
    }

    static Stream<Arguments> provideBookData() {
        return Stream.of(
                Arguments.of("Spring Boot 实战", 79.00),
                Arguments.of("Java 核心技术", 89.00),
                Arguments.of("深入理解 Java 虚拟机", 129.00)
        );
    }
}
