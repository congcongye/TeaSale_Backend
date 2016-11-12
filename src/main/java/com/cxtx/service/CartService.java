package com.cxtx.service;

import com.cxtx.entity.Cart;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import com.cxtx.model.DeleteImageModel;
import com.cxtx.model.UpdateCartModel;

import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
public interface CartService {
     Cart addToCart(Product product, double num, double price, Customer customer);
     int delete(List<DeleteImageModel> list);
     List<Cart> update(List<UpdateCartModel> list);
     List<Cart> searchAll(Customer customer);
}
