package com.cxtx.dao;

import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSaler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by ycc on 16/10/23.
 */
public interface ProductDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
   List<Product> findByProductTypeAndTeaSalerAndLevelAndLocalityAndNameAndAlive(ProductType pt, TeaSaler ts, int level, String locality, String name, int alive);
   Product findByIdAndAlive(Long id,int alive);
   List<Product> findByTeaSalerAndStateAndAliveAndType(TeaSaler teaSaler,int state,int alive,int type);
   List<Product> findByTeaSalerAndAliveAndType(TeaSaler teaSaler,int alive,int type);
   List<Product> findByAlive(int alive);
   List<Product> findByProductTypeAndAliveAndType(ProductType productType,int alive,int type);
   List<Product> findByAliveAndType(int alive,int type);
}
