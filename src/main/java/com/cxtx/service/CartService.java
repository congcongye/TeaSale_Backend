package com.cxtx.service;

import com.cxtx.entity.Cart;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.DeleteImageModel;
import com.cxtx.model.SearchCartModel;
import com.cxtx.model.UpdateCartModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/11/12.
 */
public interface CartService {
     Cart addToCart(Product product, double num,Customer customer);
     int delete(List<DeleteImageModel> list);
     List<Cart> update(List<UpdateCartModel> list);
     List<SearchCartModel> searchAll(Customer customer);
}
