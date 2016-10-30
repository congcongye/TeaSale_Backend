package com.cxtx.service;

import com.cxtx.entity.Product;

import java.util.Map;

/**
 * Created by ycc on 16/10/30.
 */
public interface ProductService {
     Map<String,Object> newProduct(Product product, Long productType_id, Long teaSeller_id);
}
