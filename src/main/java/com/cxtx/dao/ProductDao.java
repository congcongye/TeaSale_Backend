package com.cxtx.dao;

import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by ycc on 16/10/23.
 */
public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
   List<Product> findByProductTypeAndTeaSellerAndLevelAndLocalityAndNameAndAlive(ProductType pt, TeaSeller ts,int level,String locality,String name,int alive);
   Product findByIdAndAlive(Long id,int alive);
}
