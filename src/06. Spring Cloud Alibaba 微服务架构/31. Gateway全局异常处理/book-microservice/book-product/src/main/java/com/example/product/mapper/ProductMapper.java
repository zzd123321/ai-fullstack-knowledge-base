package com.example.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.product.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 扣减库存：使用条件更新防止超卖
     * 只有 stock >= quantity 时才会更新成功
     */
    @Update("UPDATE product SET stock = stock - #{quantity}, " +
            "sales = sales + #{quantity} " +
            "WHERE id = #{productId} AND stock >= #{quantity}")
    int deductStock(@Param("productId") Long productId,
                    @Param("quantity") Integer quantity);
}