package com.example.order.controller;

import com.example.common.model.Result;
import com.example.order.model.dto.OrderCreateRequest;
import com.example.order.model.Order;
import com.example.order.service.OrderService;
import com.example.order.service.vo.OrderDetailVO;
import com.example.order.service.vo.OrderVO;
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
    public Result<OrderDetailVO> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return Result.success(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public Result<OrderDetailVO> getOrderDetail(@PathVariable("id") Long id) {

        return Result.success(orderService.getOrderDetail(id));
    }

    @GetMapping
    public Result<List<OrderVO>> list() {
        return Result.success(orderService.listAll());

    }

    @GetMapping("/user/{userId}")
    public Result<List<OrderVO>> listByUserId(@PathVariable("userId") Long userId) {
        return Result.success(orderService.listByUserId(userId));
    }

    @GetMapping("/status/{status}")
    public Result<List<OrderVO>> listByStatus(@PathVariable("status") String status) {
        return Result.success(orderService.listByStatus(status));
    }

    @PutMapping("/{id}/cancel")
    public Result<Boolean> cancelOrder(@PathVariable("id") Long id) {

        return Result.success(orderService.cancelOrder(id));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable("id") Long id) {
        return Result.success(orderService.removeById(id));
    }
}