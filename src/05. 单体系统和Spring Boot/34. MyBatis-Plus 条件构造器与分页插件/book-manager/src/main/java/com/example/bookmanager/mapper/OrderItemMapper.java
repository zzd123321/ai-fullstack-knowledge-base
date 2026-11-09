package com.example.bookmanager.mapper;

import com.example.bookmanager.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    List<OrderItem> selectByOrderId(Long orderId);
    int insert(OrderItem orderItem);
    int batchInsert(@Param("items") List<OrderItem> items);
    int deleteByOrderId(Long orderId);
}