package com.cxtx.dao;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
public interface OrderItemDao extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrderenAndAlive(OrderEn orderen, int alive);
    OrderItem findByIdAndAlive(Long id,int alive);
    List<OrderItem> findByProductAndAlive(Product product, int alive);
    List<OrderItem> findByAlive(int alive);

    @Query("select p from OrderItem p where p.product.id = :id and p.alive = :alive and p.orderen.createDate between :start and :end")
    List<OrderItem> findByProductAndAliveAndCreateDate(@Param("id")Long id,@Param("alive") int alive,@Param("start") Date start,@Param("end") Date end);

    @Query("select p from OrderItem p where p.orderen.customer.id = :id and p.alive = :alive and p.orderen.state = :state")
    List<OrderItem> findByCustomerAndAliveAndState(@Param("id")Long id,@Param("alive")int alive,@Param("state") int state);

}
