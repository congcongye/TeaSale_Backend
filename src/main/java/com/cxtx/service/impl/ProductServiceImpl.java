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


    /**
     * 茶产品的新增,验证了product,productType,teaseller不能未空,product里面的填入信息,应该在前台进行验证
     * @param product
     * @param productType_id
     * @param teaSeller_id
     * @return
     */
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
        if(ts ==null){
            result.put("code",3);
            result.put("msg","茶农不存在或未审核通过");
            return result;
        }
        if(product==null){
            result.put("code",3);
            result.put("msg","产品信息未填");
            return result;
        }
        product.setProductType(pt);
        product.setTeaSeller(ts);
        product.setAlive(1);//存在
        product.setState(0);//未上架
        productDao.save(product);
        result.put("code",1);
        result.put("msg","新增产品成功");
        return result;
    }

}
