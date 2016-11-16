package com.cxtx.service;

import com.cxtx.entity.OrderEn;
import com.cxtx.model.CreateOrderModel;

/**
 * Created by jinchuyang on 16/11/15.
 */
public interface OrderService {
    OrderEn insertOrder(CreateOrderModel createOrderModel);
}
