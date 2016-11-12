package com.cxtx.dao;

import com.cxtx.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ycc on 16/10/22.
 */
public interface ProductTypeDao extends JpaRepository<ProductType,Long> {
     ProductType findByIdAndAlive(Long id,int alive);
     List<ProductType> findByAliveAndState(int alive, int state);//存在且可用的茶产品类型
     List<ProductType> findByNameAndDescriptAndStateAndAlive(String name,String descript,int state,int alive);
}
