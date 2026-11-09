package com.example.bookmanager.data;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@MybatisPlusTest
@Import(TestMybatisPlusConfig.class)
@DisplayName("BookMapper 数据层测试")
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        // 清空数据集
        bookMapper.delete(null);

        // 准备测试数据
        book1 = new Book();
        book1.setTitle("Spring Boot 实战");
        book1.setAuthor("Craig Walls");
        book1.setPrice(79.0);
        book1.setStock(10);
        book1.setCategory("编程");
        book1.setSales(0);
        bookMapper.insert(book1);

        book2 = new Book();
        book2.setTitle("Java 核心技术");
        book2.setAuthor("Cay Horstmann");
        book2.setPrice(89.0);
        book2.setStock(8);
        book2.setCategory("编程");
        book2.setSales(0);
        bookMapper.insert(book2);

        book3 = new Book();
        book3.setTitle("百年孤独");
        book3.setAuthor("马尔克斯");
        book3.setPrice(55.0);
        book3.setStock(6);
        book3.setCategory("小说");
        book3.setSales(0);
        bookMapper.insert(book3);
    }

    @Nested
    @DisplayName("基础CRUD测试")
    class BasicCrudTest {

        @Test
        @DisplayName("插入图书")
        void testInsert() {
            Book newBook = new Book();
            newBook.setTitle("深入理解 Java 虚拟机");
            newBook.setAuthor("周志明");
            newBook.setPrice(129.0);
            newBook.setStock(5);
            newBook.setCategory("编程");
            newBook.setSales(0);

            int rows = bookMapper.insert(newBook);

            assertThat(rows).isEqualTo(1);
            assertThat(newBook.getId()).isNotNull();
        }

        @Test
        @DisplayName("根据Id查询存在的图书")
        void testSelectByIdFound() {

            Book result = bookMapper.selectById(book1.getId());
            assertThat(result).isNotNull();
            assertThat(result.getTitle()).isEqualTo("Spring Boot 实战");
            assertThat(result.getAuthor()).isEqualTo("Craig Walls");
        }

        @Test
        @DisplayName("根据 ID 查询不存在的图书")
        void testSelectByIdNotFound() {
            Book result = bookMapper.selectById(999L);

            assertThat(result).isNull();
        }

        @Test
        @DisplayName("查询所有图书")
        void testSelectList() {

            List<Book> books = bookMapper.selectList(null);

            assertThat(books).hasSize(3);
            assertThat(books).extracting(Book::getTitle)
                    .containsExactlyInAnyOrder("Spring Boot 实战", "Java 核心技术","百年孤独");
        }

        @Test
        @DisplayName("更新图书")
        void testUpdateById() {
            book1.setPrice(99.0);
            int rows = bookMapper.updateById(book1);

            assertThat(rows).isEqualTo(1);

            Book updated = bookMapper.selectById(book1.getId());
            assertThat(updated.getPrice()).isEqualTo(99.0);
        }

        @Test
        @DisplayName("删除图书")
        void testDeleteById() {
            int rows = bookMapper.deleteById(book1.getId());

            assertThat(rows).isEqualTo(1);
            assertThat(bookMapper.selectById(book1.getId())).isNull();
            assertThat(bookMapper.selectCount(null)).isEqualTo(2);
        }

        @Test
        @DisplayName("统计总数")
        void testSelectCount() {
            Long count = bookMapper.selectCount(null);

            assertThat(count).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("条件构造器测试")
    class WrapperTests {

        @Test
        @DisplayName("使用Wrapper按作者查询")
        void testSelectByAuthorWithWrapper() {

            LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Book::getAuthor, "Craig Walls");

            List<Book> books = bookMapper.selectList(queryWrapper);

            assertThat(books).hasSize(1);
            assertThat(books.get(0).getTitle()).isEqualTo("Spring Boot 实战");
        }
    }


    @Nested
    @DisplayName("@Select 注解方法测试")
    class AnnotatedQueryTests {

        @Test
        @DisplayName("按作者精确查询")
        void testSelectByAuthor() {
            List<Book> books = bookMapper.selectByAuthor("Craig Walls");

            assertThat(books).hasSize(1);
            assertThat(books.get(0).getTitle()).isEqualTo("Spring Boot 实战");
        }

        @Test
        @DisplayName("按作者查询无结果")
        void testSelectByAuthorNotFound() {
            List<Book> books = bookMapper.selectByAuthor("不存在的作者");

            assertThat(books).isEmpty();
        }

        @Test
        @DisplayName("更新库存")
        void testUpdateStock() {
            int originalStock = book1.getStock();

            int updatedRows = bookMapper.updateStock(book1.getId(), 5);

            assertThat(updatedRows).isEqualTo(1);
            Book updated = bookMapper.selectById(book1.getId());
            assertThat(updated.getStock()).isEqualTo(originalStock + 5);
        }
    }

    @Nested
    @DisplayName("分页与排序测试")
    class PagingTests {

        @Test
        @DisplayName("分页查询第一页")
        void testSelectPageFirstPage() {
            Page<Book> page = new Page<>(1, 2);
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByAsc(Book::getId);

            IPage<Book> result = bookMapper.selectPage(page, wrapper);

            assertThat(result.getTotal()).isEqualTo(3);
            assertThat(result.getPages()).isEqualTo(2);
            assertThat(result.getRecords()).hasSize(2);
            assertThat(result.getCurrent()).isEqualTo(1);
            assertThat(result.getSize()).isEqualTo(2);
        }

        @Test
        @DisplayName("分页查询第二页")
        void testSelectPageSecondPage() {
            Page<Book> page = new Page<>(2, 2);
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByAsc(Book::getId);

            IPage<Book> result = bookMapper.selectPage(page, wrapper);

            assertThat(result.getRecords()).hasSize(1);
            assertThat(result.getCurrent()).isEqualTo(2);
        }

        @Test
        @DisplayName("分页 + 排序")
        void testSelectPageWithSort() {
            Page<Book> page = new Page<>(1, 2);
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Book::getPrice);

            IPage<Book> result = bookMapper.selectPage(page, wrapper);

            assertThat(result.getRecords()).hasSize(2);
            assertThat(result.getRecords().get(0).getPrice()).isEqualTo(89.0);
            assertThat(result.getRecords().get(1).getPrice()).isEqualTo(79.0);
        }

        @Test
        @DisplayName("分页 + 条件")
        void testSelectPageWithCondition() {
            Page<Book> page = new Page<>(1, 10);
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Book::getCategory, "编程");

            IPage<Book> result = bookMapper.selectPage(page, wrapper);

            assertThat(result.getTotal()).isEqualTo(2);
            assertThat(result.getRecords()).hasSize(2);
        }
    }

    @Test
    @DisplayName("/sql/book-test-data.sql")
    void testWithSqlFile() {
        List<Book> books = bookMapper.selectList(null);
        assertThat(books).hasSize(3);
    }


    @Test
    @Sql(
            scripts = "/sql/book-test-data.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = "/sql/cleanup.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void testWithBeforeAndAfterSql() {
        List<Book> books = bookMapper.selectList(null);
        assertThat(books).hasSize(3);
    }
}
