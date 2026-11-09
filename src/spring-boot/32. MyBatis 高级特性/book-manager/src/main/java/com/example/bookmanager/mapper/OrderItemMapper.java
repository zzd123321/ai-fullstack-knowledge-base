package com.example.bookmanager.mapper;

import com.example.bookmanager.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    // 根据OrderId获取一组OrderItem
    List<OrderItem> selectByOrderId(Long orderId);

    // 插入订单项：单个或批量
    int insert(OrderItem orderItem);
    int batchInsert(@Param("items") List<OrderItem> items);

    // 根据OrderId删除订单项
    int deleteByOrderId(Long orderId);
}
