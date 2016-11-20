package com.cxtx.dao;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
public interface OrderItemDao extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrderenAndAlive(OrderEn orderen, int alive);
}
