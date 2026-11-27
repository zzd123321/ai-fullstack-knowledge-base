package com.example.order.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.common.feign.dto.ProductDto;
import com.example.common.feign.dto.ProductQuery;
import com.example.order.model.dto.OrderCreateRequest;
import com.example.order.model.Order;
import com.example.order.service.vo.OrderDetailVO;
import com.example.order.service.vo.OrderVO;

import java.util.List;

public interface OrderService extends IService<Order> {

    OrderDetailVO createOrder(OrderCreateRequest request);

    OrderDetailVO getOrderDetail(Long orderId);

    List<OrderVO> listAll();

    List<OrderVO> listByUserId(Long userId);

    List<OrderVO> listByStatus(String status);

    boolean cancelOrder(Long orderId);

    // 通过OpenFeign调用远程的商品服务
    ProductDto getProductById(Long productId);

    List<ProductDto> query(ProductQuery query);

    void deductStock(Long productId, Integer quantity);
}