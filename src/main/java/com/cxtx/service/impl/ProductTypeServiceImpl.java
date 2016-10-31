package com.cxtx.service.impl;

import com.cxtx.dao.ProductTypeDao;
import com.cxtx.entity.ProductType;
import com.cxtx.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/22.
 */
@Service("ProductTypeService")
public class ProductTypeServiceImpl implements ProductTypeService{

    @Autowired
    private ProductTypeDao productTypeDao;

    /**
     *产品类型的新增和修改(修改只能把state变成0)
     * @param productTypes
     * @return
     */
    @Override
    public int newOrUpdateProductType(List<ProductType> productTypes){
        int succCount=0;
        for(ProductType productType:productTypes){
            if(Unique(productType)){
                productTypeDao.save(productType);
                succCount++;
            }
        }
        return succCount;
    }

    private boolean Unique(ProductType productType){//判断茶产品类型不存在或者是当前要修改的茶产品类型
        List<ProductType> ps=productTypeDao.findByNameAndDescriptAndUrlAndStateAndAlive(productType.name,productType.descript,productType.url,productType.state,1);
        boolean flag=false;
        if(null==ps || ps.isEmpty()){
            return true;
        }
        if(ps.size()==1){
            if(ps.get(0).id==productType.id){
                return true;
            }
        }
        return flag;
    }
    @Override
    public List<ProductType> getAllProductType(int state){
        List<ProductType> list = productTypeDao.findByAliveAndState(1,state);//存在且可用的全部茶产品类型,可以使用的state=1
        if(list==null){
            list=new ArrayList<ProductType>();
        }
        return list;
    }




}

