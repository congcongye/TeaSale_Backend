package com.cxtx.dao;

import com.cxtx.entity.Cart;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
public interface CartDao extends JpaRepository<Cart,Long> {
    Cart findByIdAndAlive(Long id,int alive);
    Cart findByProductAndCustomerAndAlive(Product product, Customer customer, int alive);
    List<Cart> findByCustomerAndAlive(Customer customer, int alive);
}
