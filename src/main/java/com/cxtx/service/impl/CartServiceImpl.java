package com.cxtx.service.impl;

import com.cxtx.dao.CartDao;
import com.cxtx.entity.Cart;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.DeleteImageModel;
import com.cxtx.model.SearchCartModel;
import com.cxtx.model.UpdateCartModel;
import com.cxtx.service.CartService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * @param customer
     * @return
     */
    public Cart addToCart(Product product, double num, Customer customer){
        Cart cart =cartDao.findByProductAndCustomerAndAlive(product,customer,1);
        Cart result=new Cart();
        if(cart!=null){//购物车中该用户已经有此商品,则加合两个产品数量
            cart.setNum(cart.getNum()+num);
            cart.setPrice(product.getPrice());
        }else{
            cart=new Cart();
            cart.setProduct(product);
            cart.setCustomer(customer);
            cart.setNum(num);
            cart.setPrice(product.getPrice());
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
                cartDao.save(cart);
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
                cart.setPrice(cart.getProduct().getPrice());
                Cart cart1=cartDao.save(cart);
                result.add(cart1);
            }
        }
        return result;
    }

    /**
     *获得某个消费者的购物车中所有的产品
     */
    public List<SearchCartModel> searchAll(Customer customer){
        List<Cart> list =new ArrayList<Cart>();
        list =cartDao.findByCustomerAndAlive(customer,1);
        Map<Long,ArrayList<Cart>> map =new HashMap<Long,ArrayList<Cart>>();
        List<SearchCartModel> result =new ArrayList<SearchCartModel>();
        if(!list.isEmpty()){
            for(Cart cart:list){
                Product product=cart.getProduct();
                if(product!=null){
                    TeaSaler teaSaler=product.getTeaSaler();
                    ArrayList<Cart> carts =new ArrayList<Cart>();
                    if(map.containsKey(teaSaler.getId())){
                        carts =map.get(teaSaler.getId());
                        carts.add(cart);
                    }else{
                        carts.add(cart);
                    }
                    map.put(teaSaler.getId(),carts);
                }
            }
            for(Map.Entry<Long,ArrayList<Cart>> entry:map.entrySet()){
                  SearchCartModel searchCartModel =new SearchCartModel();
                ArrayList<Cart> carts =new ArrayList<Cart>();
                carts=entry.getValue();
                searchCartModel.list=carts;
                if(carts!=null){
                    searchCartModel.teaSaler=carts.get(0).getProduct().getTeaSaler();
                }
                result.add(searchCartModel);
            }
        }
        return result;
    }

}
