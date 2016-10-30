package com.cxtx.service;

import com.cxtx.entity.Product;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/30.
 */
public interface ProductService {
     Map<String,Object> newProduct(Product product, Long productType_id, Long teaSeller_id);
     Map<String,Object> updateProduct(List<Product> products);
     Map<String,Object> startSell(List<Product> products);
     Page<Product> findByConditions(Long productType_id, String remark, String name, int level, String locality, double stock, double price,
                                    double startNum, double discount, int isFree, String teaSeller_name, int state, int pageIndex, int pageSize, String sortField, String sortOrder);
}
