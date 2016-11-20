package com.cxtx.service;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.model.CreateOrderItemModel;
import com.cxtx.model.CreateOrderModel;

import java.util.List;

/**
 * Created by jinchuyang on 16/11/15.
 */
public interface OrderItemService {

    List<OrderItem> insertItems(List<CreateOrderItemModel> createOrderItemModels);

    List<OrderItem> searchItemsByOrder(long orderId);
}
