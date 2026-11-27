package com.example.product.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.product.model.dto.ProductCreateRequest;
import com.example.product.model.Product;
import com.example.product.service.vo.ProductVO;

import java.util.List;

public interface ProductService extends IService<Product> {
    ProductVO create(ProductCreateRequest request);
    ProductVO getDetail(Long id);
    List<ProductVO> listAll();
    ProductVO update(Long id, ProductCreateRequest request);
    boolean delete(Long id);
}