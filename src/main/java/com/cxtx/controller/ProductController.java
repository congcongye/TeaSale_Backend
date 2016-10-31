package com.cxtx.controller;

import com.cxtx.entity.Product;
import com.cxtx.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/30.
 */
@Controller
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

    /**
     * 茶产品的条件查询,不需要查找的条件不写或者数字填-1,字符串填""
     * @param productType_id
     * @param remark
     * @param name
     * @param level
     * @param locality
     * @param stock
     * @param price
     * @param startNum
     * @param discount
     * @param isFree
     * @param teaSeller_name
     * @param state
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @RequestMapping(value = "/product/search", method = RequestMethod.GET)
    @ResponseBody
    public Page<Product> findProductByConditions(@RequestParam(value = "productType_id",defaultValue = "-1") Long productType_id, @RequestParam(value = "remark",defaultValue = "") String remark, @RequestParam(value = "name",defaultValue = "")String name,
                                                 @RequestParam(value = "level",defaultValue = "-1")int level, @RequestParam(value = "locality",defaultValue = "")String locality,@RequestParam(value = "stock",defaultValue = "-1") double stock,
                                                 @RequestParam(value = "price",defaultValue = "-1")double price, @RequestParam(value = "startNum",defaultValue = "-1")double startNum, @RequestParam(value = "discount",defaultValue = "-1")double discount,
                                                 @RequestParam(value = "isFree",defaultValue = "-1")int isFree, @RequestParam(value = "teaSeller_name",defaultValue = "")String teaSeller_name, @RequestParam(value = "state",defaultValue = "-1")int state,
                                                 @RequestParam(value="pageIndex", defaultValue="0") int pageIndex, @RequestParam(value="pageSize", defaultValue="10") int pageSize, @RequestParam(value="sortField", defaultValue="price") String sortField, @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){

        Page<Product> products = productService.findByConditions(productType_id,remark,name,level,locality,stock,price,
        startNum, discount, isFree, teaSeller_name,state,pageIndex,pageSize, sortField,sortOrder);
        return products;
    }

}
