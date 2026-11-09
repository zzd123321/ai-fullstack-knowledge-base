package com.example.bookmanager.repository;

import com.example.bookmanager.model.Book;
import com.example.bookmanager.repository.dto.BookSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // ---------- 基本查询 ----------
    List<Book> findByAuthor(String author);

    List<Book> findByCategory(String category);

    List<Book> findByTitleContaining(String keyword);

    List<Book> findByAuthorAndCategory(String author, String category);

    List<Book> findByAuthorOrCategory(String author, String category);

    // ---------- 范围查询 ----------
    List<Book> findByPriceBetween(Double min, Double max);

    List<Book> findByPriceLessThan(Double max);

    List<Book> findByPriceGreaterThan(Double min);

    // ---------- 排序查询 ----------
    List<Book> findByAuthorOrderByPriceAsc(String author);

    List<Book> findByCategoryOrderByPriceDesc(String category);

    // ---------- 集合查询 ----------
    List<Book> findByCategoryIn(List<String> categories);

    // ---------- 存在性查询 ----------
    boolean existsByAuthor(String author);

    long countByCategory(String category);



    // ---------- 基本 JPQL ----------
    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findBooksByAuthor(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :min AND :max")
    List<Book> findBooksByPriceRange(@Param("min") Double min, @Param("max") Double max);

    // ---------- 多条件 JPQL ----------
    @Query("SELECT b FROM Book b WHERE " +
            "(:author IS NULL OR b.author LIKE %:author%) AND " +
            "(:category IS NULL OR b.category = :category) AND " +
            "(:minPrice IS NULL OR b.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR b.price <= :maxPrice)")
    List<Book> searchBooks(@Param("author") String author,
                           @Param("category") String category,
                           @Param("minPrice") Double minPrice,
                           @Param("maxPrice") Double maxPrice);



    // 原生 SQL 查询
    @Query(value = "SELECT * FROM book WHERE title ILIKE %:keyword%", nativeQuery = true)
    List<Book> findByTitleKeywordNative(@Param("keyword") String keyword);

    // 使用 PostgreSQL 特定函数
    @Query(value = "SELECT * FROM book WHERE EXTRACT(YEAR FROM created_at) = :year", nativeQuery = true)
    List<Book> findBooksByYear(@Param("year") int year);



    @Query("SELECT new com.example.bookmanager.repository.dto.BookSummary(b.id, b.title, b.author, b.price) FROM Book b WHERE b.price > :minPrice")
    List<BookSummary> findBookSummaries(@Param("minPrice") Double minPrice);




    // ---------- 方法命名查询 + 分页 ----------
    Page<Book> findByCategory(String category, Pageable pageable);

    // ---------- @Query + 分页 ----------
    @Query("SELECT b FROM Book b WHERE b.price > :minPrice")
    Page<Book> findExpensiveBooks(@Param("minPrice") Double minPrice, Pageable pageable);



    // 批量更新价格
    @Modifying
    @Query("UPDATE Book b SET b.price = b.price * :multiplier WHERE b.category = :category")
    int updatePriceByCategory(@Param("multiplier") Double multiplier,
                              @Param("category") String category);

    // 批量删除
    @Modifying
    @Query("DELETE FROM Book b WHERE b.author = :author")
    int deleteByAuthor(@Param("author") String author);

    // 更新单条（使用 @Modifying + 原生 SQL）
    @Modifying
    @Query(value = "UPDATE book SET stock = stock - :quantity WHERE id = :id", nativeQuery = true)
    int decreaseStock(@Param("id") Long id, @Param("quantity") int quantity);
    

    // ---------- @Query JPQL ----------
    @Query("SELECT b FROM Book b WHERE b.author LIKE %:author% AND b.price BETWEEN :min AND :max")
    List<Book> searchByAuthorAndPrice(@Param("author") String author,
                                      @Param("min") Double min,
                                      @Param("max") Double max);

    @Query("SELECT b FROM Book b WHERE b.category IN :categories")
    List<Book> findByCategories(@Param("categories") List<String> categories);

    // ---------- 原生 SQL ----------
    @Query(value = "SELECT * FROM book WHERE title ILIKE %:keyword%", nativeQuery = true)
    List<Book> searchByTitleNative(@Param("keyword") String keyword);

    // ---------- 分页查询 ----------
    @Query("SELECT b FROM Book b WHERE b.price > :minPrice")
    Page<Book> findBooksAbovePrice(@Param("minPrice") Double minPrice, Pageable pageable);

    // ---------- 聚合查询 ----------
    @Query("SELECT AVG(b.price) FROM Book b")
    Double getAveragePrice();

    @Query("SELECT MAX(b.price) FROM Book b WHERE b.category = :category")
    Double getMaxPriceByCategory(@Param("category") String category);

    // ---------- 更新操作 ----------
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book b SET b.stock = b.stock + :delta WHERE b.id = :id")
    int updateStock(@Param("id") Long id, @Param("delta") int delta);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Book b WHERE b.category = :category")
    int deleteByCategory(@Param("category") String category);
}