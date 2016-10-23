package com.cxtx.dao;

import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ycc on 16/10/23.
 */
public interface ProductDao extends JpaRepository<Product,Long> {

}
