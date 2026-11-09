package com.example.bookmanager.basic;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JUnit5基础示例")
public class FirstTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("==== 所有测试开始前执行一次 ====");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("---- 每个测试开始前执行一次 ----");
    }

    @Test
    @DisplayName("第一个测试方法")
    void test1() {
        System.out.println("执行测试1");
        assertEquals(2, 1 + 1);
    }

    @Test
    @DisplayName("第二个测试方法")
    void test2() {
        System.out.println("执行测试2");
        assertTrue(5 > 3);
    }

    @AfterEach
    void afterEach() {
        System.out.println("---- 每个测试完成后执行一次 ----");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("==== 所有测试完成后执行一次 ====");
    }

    @Test
    @DisplayName("断言方法示例")
    void testAssertions() {
        // 相等断言
        assertEquals(4, 2 + 2, "2+2应该等于4");

        // 真假断言
        assertTrue("hello".length() > 3);

        // 异常断言
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Integer.parseInt("abc")
        );
        assertEquals("For input string: \"abc\"", exception.getMessage());

        // 组合断言：全部执行，即使全面的用例执行失败
        assertAll("用户信息",
                () -> assertEquals("Alice", "Alice"),
                () -> assertTrue(35 > 18),
                () -> assertNotNull("test@126.com")
        );

    }
}
