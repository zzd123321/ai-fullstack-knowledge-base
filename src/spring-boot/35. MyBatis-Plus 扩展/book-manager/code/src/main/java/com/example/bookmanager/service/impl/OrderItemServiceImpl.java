package com.example.bookmanager.service.impl;

import com.example.bookmanager.model.OrderItem;
import com.example.bookmanager.mapper.OrderItemMapper;
import com.example.bookmanager.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BookManager Team
 * @since 2026-08-31
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
