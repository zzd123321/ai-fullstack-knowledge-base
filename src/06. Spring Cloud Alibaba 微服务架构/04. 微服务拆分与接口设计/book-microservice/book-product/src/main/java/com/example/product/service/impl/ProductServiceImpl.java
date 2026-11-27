package com.example.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.product.model.dto.ProductCreateRequest;
import com.example.product.mapper.ProductMapper;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import com.example.product.service.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public ProductVO create(ProductCreateRequest request) {
        // DTO -> Entity（同名属性自动拷贝）
        Product product = BeanUtil.copyProperties(request, Product.class);
        product.setSales(0);
        this.save(product);
        // Entity -> VO
        return BeanUtil.copyProperties(product, ProductVO.class);
    }

    @Override
    public ProductVO getDetail(Long id) {
        Product product = this.getById(id);
        if (product == null) {
            return null;
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
            return null;
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
}