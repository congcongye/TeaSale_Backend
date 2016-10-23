package com.cxtx.controller;

import com.cxtx.entity.ProductType;
import com.cxtx.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/23.
 */
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 茶产品类型的新增和修改,修改只能把state变成0,即不产品新增时,该商品不可用
     * @param list
     * @return
     */
    @RequestMapping(value = "/productType/newOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> newOrUpdateProductType(@RequestBody List<ProductType> list){
        Map<String,Object> result =productTypeService.newOrUpdateProductType(list);
        return result;
    }
}
