package com.example.bookmanager.controller;

import com.example.bookmanager.model.Order;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.dto.OrderCreateRequest;
import com.example.bookmanager.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<Order> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Order order = orderService.createOrder(request.getItems());
        return Result.success(order);
    }

    @GetMapping("/{id}")
    public Result<Order> getOrderWithItems(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return Result.success(order);
    }

    @GetMapping
    public Result<List<Order>> getAllOrdersWithItems() {
        List<Order> orders = orderService.getAllOrders();
        return Result.success(orders);
    }

    @GetMapping("/status/{status}")
    public Result<List<Order>> getOrdersByStatusWithItems(@PathVariable String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return Result.success(orders);
    }

    @PutMapping("/{id}/cancel")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }
}
