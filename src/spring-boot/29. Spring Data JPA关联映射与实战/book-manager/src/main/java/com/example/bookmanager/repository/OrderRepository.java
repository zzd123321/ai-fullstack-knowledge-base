package com.example.bookmanager.repository;

import com.example.bookmanager.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByStatus(String status);


    // 方案一：JOIN FETCH
    @Query("SELECT o FROM Order o JOIN FETCH o.items i JOIN FETCH i.book WHERE o.id = :id")
    Optional<Order> findByIdWithItems(@Param("id") Long id);

    @Query("SELECT o FROM Order o JOIN FETCH o.items i JOIN FETCH i.book")
    List<Order> findAllWithItems();


    // 方案二：EntityGraph
    @EntityGraph(attributePaths = {"items", "items.book"})
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findByIdWithGraph(@Param("id") Long id);

    @EntityGraph(attributePaths = {"items", "items.book"})
    @Query("SELECT o FROM Order o")
    List<Order> findAllWithGraph();

    @EntityGraph(attributePaths = {"items", "items.book"})
    List<Order> findAll();
}
