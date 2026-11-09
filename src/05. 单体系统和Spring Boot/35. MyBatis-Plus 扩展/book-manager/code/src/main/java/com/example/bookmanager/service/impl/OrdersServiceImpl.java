package com.example.bookmanager.service.impl;

import com.example.bookmanager.model.Orders;
import com.example.bookmanager.mapper.OrdersMapper;
import com.example.bookmanager.service.OrdersService;
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
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
