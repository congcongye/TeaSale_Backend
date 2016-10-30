package com.cxtx.controller;

import com.cxtx.entity.Product;
import com.cxtx.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ycc on 16/10/30.
 */
public class ProductController {

    private ProductService productService;

    @RequestMapping(value = "/product/new", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> newProduct(@RequestBody Product product, @RequestParam (value="productType_id",defaultValue = "-1")Long productType_id,@RequestParam (value="teaSeller_id",defaultValue = "-1")Long teaSeller_id){
        Map<String,Object> result = productService.newProduct(product,productType_id,teaSeller_id);
        return result;
    }
}
