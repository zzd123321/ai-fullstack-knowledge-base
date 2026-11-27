package com.example.order.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.order.model.dto.OrderCreateRequest;
import com.example.order.model.Order;
import com.example.order.service.vo.OrderDetailVO;

import java.util.List;

public interface OrderService extends IService<Order> {

    OrderDetailVO createOrder(OrderCreateRequest request);

    OrderDetailVO getOrderDetail(Long orderId);

    List<Order> listByUserId(Long userId);

    List<Order> listByStatus(String status);

    boolean cancelOrder(Long orderId);
}