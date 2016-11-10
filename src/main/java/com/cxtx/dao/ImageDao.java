package com.cxtx.dao;

import com.cxtx.entity.Image;
import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ycc on 16/10/23.
 */
public interface ImageDao extends JpaRepository<Image,Long> {
    Image findByIdAndAlive(Long id,int alive);
    List<Image> findByProductAndTypeAndAlive(Product product, int type,int alive);
    List<Image> findByProductAndAlive(Product product,int alive);
}
