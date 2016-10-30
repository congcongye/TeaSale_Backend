package com.cxtx.service.impl;

import com.cxtx.dao.ProductDao;
import com.cxtx.dao.ProductTypeDao;
import com.cxtx.dao.TeaSellerDao;
import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSeller;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ycc on 16/10/24.
 */
public class ProductServiceImpl {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private TeaSellerDao teaSellerDao;


    public Map<String,Object> newProduct(Product product,Long productType_id,Long teaSeller_id){
        Map<String,Object> result =new HashMap<String,Object>();
        if(product==null){
            result.put("code",2);
            result.put("msg","产品信息未传入");
            return result;
        }
        ProductType pt =productTypeDao.findByIdAndAlive(productType_id,1);
        TeaSeller ts =teaSellerDao.findByIdAndStateAndAlive(teaSeller_id,1,1);//存在且审核通过的茶农
        if(pt==null){
            result.put("code",3);
            result.put("msg","产品类型信息不存在");
            return result;
        }


    }
}
