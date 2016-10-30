package com.cxtx.dao;

import com.cxtx.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ycc on 16/10/22.
 */
public interface ProductTypeDao extends JpaRepository<ProductType,Long> {
     ProductType findByIdAndAlive(Long id,int alive);
}
