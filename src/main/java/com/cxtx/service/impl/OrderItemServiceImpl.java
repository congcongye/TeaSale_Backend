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
import org.springframework.transaction.annotation.Transactional;

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
    //@Transactional
    @Override
    public List<OrderItem> insertItems(List<CreateOrderItemModel> createOrderItemModels) {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        List<Product> products =  new ArrayList<Product>();
        OrderEn orderEn = null;
        Product product;
        double totalMoney = 0;
        for (CreateOrderItemModel createOrderItemModel :createOrderItemModels) {
            long productId = createOrderItemModel.productId;
           // long orderEnId = createOrderItemModel.orderEnId;
            product = productDao.findOne(productId);
            //orderEn = orderEnDao.findOne(orderEnId);
            if (product == null || product.getAlive() == 0 || orderEn == null || orderEn.getAlive() == 0){
                break;
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setAlive(1);
            if (product.getStock() < createOrderItemModel.num){
                break;
            }
            product.setStock(product.getStock()-createOrderItemModel.num);
            products.add(product);
            orderItem.setNum(createOrderItemModel.num);
            orderItem.setProduct(product);
            orderItem.setOrderen(orderEn);
            orderItem.setTotalPrice(createOrderItemModel.num * product.getPrice() * product.getDiscount());
            totalMoney += createOrderItemModel.num * product.getPrice() * product.getDiscount();
           // orderItem = orderItemDao.save(orderItem);
            orderItems.add(orderItem);
        }
        if (orderItems.size()!=0&&orderItems.size()==createOrderItemModels.size()){
            orderItems = orderItemDao.save(orderItems);
            products = productDao.save(products);
            orderEn.setTotalPrice(totalMoney + orderEn.getLogistic());
            orderEn.setState(1);
            orderEnDao.save(orderEn);
            return orderItems;
        }
        return null;
    }

    @Override
    public List<OrderItem> searchItemsByOrder(long orderId) {
        OrderEn orderEn = orderEnDao.findOne(orderId);
        if (orderEn == null || orderEn.getAlive() ==0){
            return  null;
        }
        List<OrderItem> orderItems = orderItemDao.findByOrderenAndAlive(orderEn, 1);
        return orderItems;
    }
}
