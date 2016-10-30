package com.cxtx.controller;

import com.cxtx.entity.Product;
import com.cxtx.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/30.
 */
public class ProductController {

    private ProductService productService;

    /**
     * 茶产品的新增,product对象中必须传入的数据应该由前台来限制
     * @param product
     * @param productType_id
     * @param teaSeller_id
     * @return
     */
    @RequestMapping(value = "/product/new", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> newProduct(@RequestBody Product product, @RequestParam (value="productType_id",defaultValue = "-1")Long productType_id,@RequestParam (value="teaSeller_id",defaultValue = "-1")Long teaSeller_id){
        Map<String,Object> result = productService.newProduct(product,productType_id,teaSeller_id);
        return result;
    }

    /**
     * 茶产品的批量修改,哪些数据能修改,哪些不能应该由前台控制
     * @param productList
     * @return
     */
    @RequestMapping(value = "/product/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateProduct(@RequestBody List<Product> productList){
        Map<String,Object> result = productService.updateProduct(productList);
        return result;
    }

    /**
     * 茶产品的批量上架处理
     * @param productList
     * @return
     */
    @RequestMapping(value = "/product/startSell", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> startSell(@RequestBody List<Product> productList){
        Map<String,Object> result = productService.startSell(productList);
        return result;
    }



}
