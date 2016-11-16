package com.cxtx.service.impl;

import com.cxtx.dao.OrderEnDao;
import com.cxtx.dao.OrderItemDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.entity.Product;
import com.cxtx.model.CreateOrderItemModel;
import com.cxtx.service.OrderItemService;
import com.cxtx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/11/12.
 */
@Service("OrderItemServiceImpl")
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderEnDao orderEnDao;
    @Autowired
    private OrderItemDao orderItemDao;

    /**
     *
     * @param createOrderItemModels
     * @return
     */
    @Override
    public List<OrderItem> insertItems(List<CreateOrderItemModel> createOrderItemModels) {
        List<OrderItem> list = new ArrayList<OrderItem>();
        for (CreateOrderItemModel createOrderItemModel :createOrderItemModels) {
            long productId = createOrderItemModel.productId;
            long orderEnId = createOrderItemModel.orderEnId;
            Product product = productDao.findOne(productId);
            OrderEn orderEn = orderEnDao.findOne(orderEnId);
            if (product == null || product.getAlive() == 0 || orderEn == null || orderEn.getAlive() == 0){
                continue;
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setAlive(1);
            orderItem.setNum(createOrderItemModel.num);
            orderItem.setProduct(product);
            orderItem.setOrderen(orderEn);
            orderItem.setTotalPrice(createOrderItemModel.num * product.getPrice() * product.getDiscount());
            orderItem = orderItemDao.save(orderItem);
            list.add(orderItem);
        }
        return list;
    }
}
