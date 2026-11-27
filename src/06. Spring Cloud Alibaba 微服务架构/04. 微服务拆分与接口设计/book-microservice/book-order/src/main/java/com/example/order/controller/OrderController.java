package com.example.order.controller;

import com.example.order.model.dto.OrderCreateRequest;
import com.example.order.model.Order;
import com.example.order.service.OrderService;
import com.example.order.service.vo.OrderDetailVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDetailVO createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderDetailVO getOrderDetail(@PathVariable("id") Long id) {
        return orderService.getOrderDetail(id);
    }

    @GetMapping
    public List<Order> list() {
        return orderService.list();
    }

    @GetMapping("/user/{userId}")
    public List<Order> listByUserId(@PathVariable("userId") Long userId) {
        return orderService.listByUserId(userId);
    }

    @GetMapping("/status/{status}")
    public List<Order> listByStatus(@PathVariable("status") String status) {
        return orderService.listByStatus(status);
    }

    @PutMapping("/{id}/cancel")
    public Boolean cancelOrder(@PathVariable("id") Long id) {
        return orderService.cancelOrder(id);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return orderService.removeById(id);
    }
}