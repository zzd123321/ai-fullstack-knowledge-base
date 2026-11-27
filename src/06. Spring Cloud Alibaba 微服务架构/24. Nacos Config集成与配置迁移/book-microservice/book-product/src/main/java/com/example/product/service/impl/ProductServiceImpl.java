package com.example.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.common.feign.dto.ProductDto;
import com.example.common.feign.dto.ProductQuery;
import com.example.common.exception.BusinessException;
import com.example.common.exception.ParamException;
import com.example.common.exception.ResourceNotFoundException;
import com.example.product.model.dto.ProductCreateRequest;
import com.example.product.mapper.ProductMapper;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import com.example.product.service.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public ProductVO create(ProductCreateRequest request) {
        // DTO -> Entity（同名属性自动拷贝）
        Product product = BeanUtil.copyProperties(request, Product.class);

        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new ParamException("价格不能为空或负数");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new ParamException("库存不能为空或负数");
        }

        product.setSales(0);
        this.save(product);
        // Entity -> VO
        return BeanUtil.copyProperties(product, ProductVO.class);
    }

    @Override
    public ProductVO getDetail(Long id) {
        Product product = this.getById(id);
        if (product == null) {
            throw new ResourceNotFoundException("商品", id);
        }
        return BeanUtil.copyProperties(product, ProductVO.class);
    }

    @Override
    public List<ProductVO> listAll() {
        return BeanUtil.copyToList(this.list(), ProductVO.class);
    }

    @Override
    public ProductVO update(Long id, ProductCreateRequest request) {
        Product product = this.getById(id);
        if (product == null) {
            throw new ResourceNotFoundException("商品", id);
        }
        // 只覆盖 request 中存在的字段，id / sales / createdAt 保留原值
        BeanUtil.copyProperties(request, product);
        this.updateById(product);
        return BeanUtil.copyProperties(product, ProductVO.class);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public ProductDto getBookById(Long id) {
        Product product = this.getById(id);
        if (product == null) {
            throw new ResourceNotFoundException("商品", id);
        }
        return BeanUtil.copyProperties(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> query(ProductQuery query) {
        // 基于Mybatis-Plus的条件构建器来执行检索操作
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

        if (query != null) {
            if (query.getKeyword() != null && !query.getKeyword().trim().isEmpty()) {
                wrapper.and(w -> w.like(Product::getTitle, query.getKeyword())
                        .or()
                        .like(Product::getAuthor, query.getKeyword()));
            }
            if (query.getCategory() != null && !query.getCategory().trim().isEmpty()) {
                wrapper.eq(Product::getCategory, query.getCategory());
            }
            if (query.getMinPrice() != null) {
                wrapper.ge(Product::getPrice, query.getMinPrice());
            }
            if (query.getMaxPrice() != null) {
                wrapper.le(Product::getPrice, query.getMaxPrice());
            }
        }
        wrapper.orderByDesc(Product::getCreatedAt);

        // 限制最大返回数量，避免全表扫描
        wrapper.last("LIMIT 100");

        return this.list(wrapper).stream()
                .map(p -> BeanUtil.copyProperties(p, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deductStock(Long productId, Integer quantity) {
        if(productId == null || quantity == null || quantity <= 0) {
            throw new ParamException("商品Id和扣减数量不能为空，数量必须大于0");
        }

        int affectedRows = baseMapper.deductStock(productId, quantity);
        if(affectedRows == 0) {
            // 可能是商品不存在，或者库存不足
            Product product = this.getById(productId);
            if(product == null) {
                throw new ResourceNotFoundException("商品", productId);
            }
            throw new BusinessException("库存不足，当前库存：" + product.getStock()
                 + "，需要扣减：" + quantity);
        }

        log.info("扣减库存成功，productId={}，quantity={}", productId, quantity);
    }
}