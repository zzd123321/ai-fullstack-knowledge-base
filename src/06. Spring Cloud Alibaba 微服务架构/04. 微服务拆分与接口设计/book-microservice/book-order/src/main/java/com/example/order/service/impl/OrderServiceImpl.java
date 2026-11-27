package com.example.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.order.model.dto.OrderCreateRequest;
import com.example.order.model.dto.OrderItemRequest;
import com.example.order.mapper.ItemMapper;
import com.example.order.mapper.OrderItemMapper;
import com.example.order.mapper.OrderMapper;
import com.example.order.model.Item;
import com.example.order.model.Order;
import com.example.order.model.OrderItem;
import com.example.order.service.OrderService;
import com.example.order.service.vo.OrderDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final ItemMapper itemMapper;
    private final OrderItemMapper orderItemMapper;

    private static final DateTimeFormatter ORDER_NUMBER_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static final AtomicInteger SEQUENCE = new AtomicInteger(0);

    /**
     * 创建订单
     * 业务步骤：
     * 1. 生成唯一订单编号
     * 2. 保存订单主表（orders）
     * 3. 为每个商品创建快照（item）
     * 4. 创建订单明细（order_item）
     * 5. 计算订单总金额并更新订单主表
     * 全部在一个事务中完成。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetailVO createOrder(OrderCreateRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("订单项不能为空");
        }

        // 1. 保存订单主表
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setUserId(request.getUserId());
        order.setTotalAmount(0.0);
        order.setStatus("CREATED");
        this.save(order);

        // 2. 遍历订单项，逐个创建商品快照和订单明细
        double totalAmount = 0.0;
        List<OrderDetailVO.OrderItemVO> itemVOs = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {

            // 2.1 创建商品快照：从 DTO 拷贝到 Item Entity
            Item item = BeanUtil.copyProperties(itemReq, Item.class);
            itemMapper.insert(item);

            // 2.2 创建订单明细：从 DTO 拷贝到 OrderItem Entity，再补充关联字段
            OrderItem orderItem = BeanUtil.copyProperties(itemReq, OrderItem.class);
            orderItem.setOrderId(order.getId());
            orderItem.setItemId(item.getId());
            orderItemMapper.insert(orderItem);

            // 2.3 组装返回的 VO（从 Item + OrderItem 合并）
            OrderDetailVO.OrderItemVO itemVO = new OrderDetailVO.OrderItemVO();
            BeanUtil.copyProperties(item, itemVO);          // itemId, productId, title, author, price
            BeanUtil.copyProperties(orderItem, itemVO);     // quantity, price（覆盖）
            itemVO.setSubtotal(item.getPrice() * orderItem.getQuantity());

            itemVOs.add(itemVO);

            // 2.4 累加总金额
            totalAmount += itemVO.getSubtotal();
        }

        // 3. 更新订单总金额
        order.setTotalAmount(totalAmount);
        this.updateById(order);

        // 4. 组装返回
        OrderDetailVO detailVO = new OrderDetailVO();
        detailVO.setOrder(order);
        detailVO.setItems(itemVOs);
        return detailVO;
    }

    /**
     * 查询订单详情
     */
    @Override
    public OrderDetailVO getOrderDetail(Long orderId) {

        Order order = this.getById(orderId);
        if (order == null) {
            throw new RuntimeException("Order不存在");
        }

        // 1. 查询订单明细
        LambdaQueryWrapper<OrderItem> itemWrapper = new LambdaQueryWrapper<>();
        itemWrapper.eq(OrderItem::getOrderId, orderId);
        List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);

        // 2. 批量查询商品快照（避免 N+1）
        List<OrderDetailVO.OrderItemVO> itemVOs = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            Item item = itemMapper.selectById(orderItem.getItemId());
            if (item == null) continue;

            OrderDetailVO.OrderItemVO itemVO = new OrderDetailVO.OrderItemVO();
            BeanUtil.copyProperties(item, itemVO);
            BeanUtil.copyProperties(orderItem, itemVO);
            itemVO.setSubtotal(orderItem.getPrice() * orderItem.getQuantity());
            itemVOs.add(itemVO);
        }

        OrderDetailVO detailVO = new OrderDetailVO();
        detailVO.setOrder(order);
        detailVO.setItems(itemVOs);
        return detailVO;
    }

    @Override
    public List<Order> listByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, userId)
                .orderByDesc(Order::getCreatedAt));
    }

    @Override
    public List<Order> listByStatus(String status) {
        return this.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getStatus, status)
                .orderByDesc(Order::getCreatedAt));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelOrder(Long orderId) {
        Order order = this.getById(orderId);
        if (order == null) {
            throw new RuntimeException("Order不存在");
        }
        if (!"CREATED".equals(order.getStatus())) {
            throw new RuntimeException("只有待支付订单可以取消，当前状态: " + order.getStatus());
        }
        order.setStatus("CANCELLED");
        return this.updateById(order);
    }

    /**
     * 生成订单编号：ORD + yyyyMMddHHmmss + 3位序列号
     */
    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(ORDER_NUMBER_FORMAT);
        int seq = SEQUENCE.incrementAndGet() % 1000;
        return String.format("ORD%s%03d", timestamp, seq);
    }
}