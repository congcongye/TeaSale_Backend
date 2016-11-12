package com.cxtx.service.impl;

import com.cxtx.dao.CartDao;
import com.cxtx.entity.Cart;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import com.cxtx.model.DeleteImageModel;
import com.cxtx.model.UpdateCartModel;
import com.cxtx.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
@Service("CartServiceImpl")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    /**
     * 把产品加入购物车,注意要合并重复的商品
     * @param product
     * @param num
     * @param price
     * @param customer
     * @return
     */
    public Cart addToCart(Product product, double num, double price, Customer customer){
        Cart cart =cartDao.findByProductAndCustomerAndAlive(product,customer,1);
        Cart result=new Cart();
        if(cart!=null){//购物车中该用户已经有此商品,则加合两个产品数量
            cart.setNum(cart.getNum()+num);
        }else{
            cart=new Cart();
            cart.setProduct(product);
            cart.setNum(num);
            cart.setPrice(price);
            cart.setJoinDate(new Date());
        }
        result=cartDao.save(cart);
        return  result;
    }

    /**
     * 购物车商品的批量删除
     * @param list
     * @return
     */
    public int delete(List<DeleteImageModel> list){
        int succCount=0;
        for(DeleteImageModel deleteImageModel:list){
            Cart cart =cartDao.findByIdAndAlive(deleteImageModel.id,1);
            if(cart!=null){
                cart.setAlive(0);
                succCount++;
            }
        }
        return succCount;
    }

    /**
     * 购物车的批量修改
     * @param list
     * @return
     */
    public List<Cart> update(List<UpdateCartModel> list){
        List<Cart> result =new ArrayList<Cart>();
        for(UpdateCartModel updateCartModel:list){
            Cart cart=cartDao.findByIdAndAlive(updateCartModel.id,1);
            if(cart!=null){
                cart.setNum(updateCartModel.num);
                Cart cart1=cartDao.save(cart);
                result.add(cart1);
            }
        }
        return result;
    }

    /**
     *获得某个消费者的购物车中所有的产品
     */
    public List<Cart> searchAll(Customer customer){
        List<Cart> list =new ArrayList<Cart>();
        list =cartDao.findByCustomerAndAlive(customer,1);
        return list;
    }
}