package com.example.product.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.example.common.feign.dto.ProductDto;
import com.example.common.feign.dto.ProductQuery;
import com.example.product.model.dto.ProductCreateRequest;
import com.example.product.model.Product;
import com.example.product.service.vo.ProductVO;

import java.util.List;

public interface ProductService extends IService<Product> {

    // 面向前端的请求调用方法：返回VO对象
    ProductVO create(ProductCreateRequest request);
    ProductVO getDetail(Long id);
    List<ProductVO> listAll();
    ProductVO update(Long id, ProductCreateRequest request);
    boolean delete(Long id);

    // 面向跨服务请求的调用方法：返回DTO对象
    ProductDto getBookById(Long id);
    List<ProductDto> query(ProductQuery query);
    void deductStock(Long productId, Integer quantity);
}