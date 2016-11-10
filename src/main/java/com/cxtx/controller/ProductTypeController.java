package com.cxtx.controller;

import com.cxtx.entity.ProductType;
import com.cxtx.model.CreateProductTypeModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateProductTypeModel;
import com.cxtx.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/23.
 */
@Controller
public class ProductTypeController extends ApiController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 茶产品类型的新增
     * @param list
     * @return
     */
    @RequestMapping(value = "/productTypes/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult newOrUpdateProductType(@RequestBody List<CreateProductTypeModel> list){
        checkParameter(list!=null&&!list.isEmpty(),"productTypes are empty");
        int succCount =0;
        try {
            succCount = productTypeService.newProductType(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(succCount!=list.size()){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(list.size()-succCount));
        }
        return ServiceResult.success("all succeed");
    }

    /**
     * 传入参数1,获得所有可以使用的茶产品,传入参数0获得不能使用的茶产品
     * @return
     */
    @RequestMapping(value = "/productTypes/getAllProductType", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getAllProductType(@RequestParam (value ="state",defaultValue = "1")int state){
        List<ProductType> result = productTypeService.getAllProductType(state);
        return ServiceResult.success(result);
    }

    /**
     * 修改商品类型,修改只能把state变成0,即该商品不可用
     * @param list
     * @return
     */
    @RequestMapping(value = "/productTypes/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateProductType(@RequestBody List<UpdateProductTypeModel> list){
        checkParameter(list!=null&&!list.isEmpty(),"productTypes are empty");
        int succCount = productTypeService.updateProductType(list);
        if(succCount!=list.size()){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(list.size()-succCount));
        }
        return ServiceResult.success("all succeed");
    }
}
