package com.cxtx.dao;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.entity.TeaSaler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
public interface OrderEnDao extends JpaRepository<OrderEn,Long> ,JpaSpecificationExecutor<OrderEn>{
    List<OrderEn> findByAlive(int alive);

    List<OrderEn> findByTeaSalerAndAlive(TeaSaler teaSaler, int alive);
}
