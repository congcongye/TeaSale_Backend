package com.cxtx.service.impl;

import com.cxtx.dao.ProductTypeDao;
import com.cxtx.entity.ProductType;
import com.cxtx.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/22.
 */
public class ProductTypeServiceImpl implements ProductTypeService{

    @Autowired
    private ProductTypeDao productTypeDao;

    /**
     *产品类型的新增和修改(修改只能把state变成0)
     * @param productTypes
     * @return
     */
    public Map<String,Object> newOrUpdateProductType(List<ProductType> productTypes){
        Map<String,Object> result =new HashMap<String,Object>();
        if(productTypes==null || productTypes.isEmpty()){
            result.put("code",2);
            result.put("msg","信息未传入");
            return result;
        }
        int succCount=0;
        for(ProductType productType:productTypes){
            productTypeDao.save(productType);
            succCount++;
        }
        if(succCount!=productTypes.size()){
            result.put("code",3);
            result.put("msg","成功的数量是: "+succCount+" ; 失败的数量是: "+(productTypes.size()-succCount));
            return result;
        }
        result.put("code",1);
        result.put("msg","全部操作成功");
        return result;
    }






}

