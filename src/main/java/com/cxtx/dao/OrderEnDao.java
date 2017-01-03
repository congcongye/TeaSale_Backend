package com.cxtx.dao;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.entity.TeaSaler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
public interface OrderEnDao extends JpaRepository<OrderEn,Long> ,JpaSpecificationExecutor<OrderEn>{
    List<OrderEn> findByAlive(int alive);

    List<OrderEn> findByTeaSalerAndAlive(TeaSaler teaSaler, int alive);

    @Query("select p from  OrderEn p where p.teaSaler.id = :id and p.alive = :alive and p.createDate between :start and :end")
    List<OrderEn> findByTeaSalerAndAliveAndCreateDate(@Param("id")Long id, @Param("alive") int alive, @Param("start") Date start,@Param("end") Date end);

}
