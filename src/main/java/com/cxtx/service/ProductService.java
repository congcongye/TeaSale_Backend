package com.cxtx.service;

import com.cxtx.entity.Product;
import com.cxtx.model.CommentModel;
import com.cxtx.model.CreateProductModel;
import com.cxtx.model.StartSellProductModel;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * Created by ycc on 16/10/30.
 */
public interface ProductService {
     Product newProduct(Product product);
     List<Product> updateProduct(List<CreateProductModel> products);
     int startSell(List<StartSellProductModel> products);
     Page<Product> findByConditions(Long productType_id, String remark, String name, int level, String locality, double stock, double lowPrice,double highPrice,
                                    double startNum, double discount, int isFree, String teaSeller_name, int state,Long teaSaler_id, int pageIndex, int pageSize, String sortField, String sortOrder);
     Boolean isUnique(Product p);

    List<Product> commend();

    CommentModel getComment(Long id);
    int downProduct(Long productId);
}
