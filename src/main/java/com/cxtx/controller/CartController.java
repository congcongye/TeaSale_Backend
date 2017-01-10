package com.cxtx.controller;

import com.cxtx.dao.CustomerDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.Cart;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.DeleteImageModel;
import com.cxtx.model.SearchCartModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateCartModel;
import com.cxtx.service.CartService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/11/12.
 */
@Controller
public class CartController extends ApiController{

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;


    /**
     * 添加商品到购物车,同时更新购物车的价格
     * @param product_id
     * @param num
     * @param customer_id
     * @return
     */
    @RequestMapping(value = "/cart/addToCart", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult addToCart(@RequestParam(value = "product_id",defaultValue = "-1")Long product_id,
                                   @RequestParam(value = "num",defaultValue = "-1")double num,
                                   @RequestParam(value = "customer_id",defaultValue = "-1")Long customer_id){
        Product product=productDao.findByIdAndAlive(product_id,1);
        Customer customer=customerDao.findByIdAndAlive(customer_id,1);
        checkParameter(product!=null,"产品为空");
        checkParameter(customer!=null,"消费者不存在");
        checkParameter(num>=0,"产品数量有误");
        Cart cart=cartService.addToCart(product,num,customer);
        return ServiceResult.success(cart);
    }

    /**
     * 购物车的批量删除
     * @param list
     * @return
     */
    @RequestMapping(value = "/carts/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult delete(@RequestBody List<DeleteImageModel> list){
        checkParameter(list!=null&&!list.isEmpty(),"购物车为空");
        int succCount =0;
        succCount=cartService.delete(list);
        if(succCount!=list.size()){
            return ServiceResult.fail(500, "成功的数量是: "+succCount+" ; 失败的数量是: "+(list.size()-succCount));
        }
        return ServiceResult.success("全部删除成功");
    }

    @RequestMapping(value = "/carts/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult update(@RequestBody List<UpdateCartModel> list){
        checkParameter(list!=null&&!list.isEmpty(),"购物车为空");
        List<Cart> result = new ArrayList<Cart>();
        result=cartService.update(list);
        if(result.size()!=list.size()){
            return ServiceResult.fail(500, "成功的数量是: "+result.size()+" ; 失败的数量是: "+(list.size()-result.size()));

        }
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/carts/searchAll", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult searchAll( @RequestParam(value = "customer_id",defaultValue = "-1")Long customer_id){
        Customer customer =customerDao.findByIdAndAlive(customer_id,1);
        checkParameter(customer!=null,"消费者不存在");
        List<SearchCartModel> result =new ArrayList<SearchCartModel>();
        result = cartService.searchAll(customer);
        return ServiceResult.success(result);
    }

}
