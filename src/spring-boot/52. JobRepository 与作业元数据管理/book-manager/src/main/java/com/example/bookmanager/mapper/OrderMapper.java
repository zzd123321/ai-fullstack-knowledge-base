package com.example.bookmanager.mapper;

import com.example.bookmanager.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    // 基础 CRUD
    Order selectById(Long id);
    List<Order> selectAll();
    int insert(Order order);
    int update(Order order);
    int deleteById(Long id);

    // 关联查询（带订单项和图书）
    Order selectOrderWithItems(Long id);
    List<Order> selectAllWithItems();

    // 动态条件查询
    List<Order> searchOrders(@Param("status") String status,
                             @Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate);
}