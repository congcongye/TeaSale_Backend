package com.cxtx.dao;

import com.cxtx.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ycc on 16/11/12.
 */
public interface OrderItemDao extends JpaRepository<OrderItem,Long> {
}