package com.cxtx.service;

import com.cxtx.entity.ProductType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/22.
 */
public interface ProductTypeService {

     Map<String,Object> newOrUpdateProductType(List<ProductType> productTypes);
     List<ProductType> getAllProductType(int state);
}
