package com.example.bookmanager.controller;

import com.example.bookmanager.model.Order;
import com.example.bookmanager.model.Result;
import com.example.bookmanager.model.dto.OrderCreateRequest;
import com.example.bookmanager.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)   // 可添加，表明创建成功
    public Result<Order> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Order order = orderService.createOrder(request.getItems());
        return Result.success(order);
    }

    @GetMapping("/{id}")
    public Result<Order> getOrderWithItems(@PathVariable Long id) {
        return Result.success(orderService.getOrderWithItems(id));
    }

    @GetMapping
    public Result<List<Order>> getAllOrdersWithItems() {
        return Result.success(orderService.getAllOrdersWithItems());
    }

    @GetMapping("/search")
    public Result<List<Order>> searchOrders(@RequestParam(required = false) String status,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate + "T00:00:00") : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate + "T23:59:59") : null;
        return Result.success(orderService.searchOrders(status, start, end));
    }

    @PutMapping("/{id}/cancel")
    public Result<Void> cancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }
}