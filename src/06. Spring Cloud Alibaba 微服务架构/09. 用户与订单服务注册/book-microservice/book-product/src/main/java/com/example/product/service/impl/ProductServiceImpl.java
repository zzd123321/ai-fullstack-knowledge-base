package com.example.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.common.exception.ParamException;
import com.example.common.exception.ResourceNotFoundException;
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
}