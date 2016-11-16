package com.cxtx.service.impl;

import com.cxtx.dao.CustomerDao;
import com.cxtx.dao.OrderEnDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Customer;
import com.cxtx.entity.OrderEn;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.CreateOrderModel;
import com.cxtx.service.OrderItemService;
import com.cxtx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ycc on 16/11/12.
 */
@Service("OrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TeaSalerDao teaSalerDao;
    @Autowired
    private OrderEnDao orderEnDao;

    @Override
    public OrderEn insertOrder(CreateOrderModel createOrderModel) {
        long customerId = createOrderModel.customerId;
        long teaSalerId = createOrderModel.teaSalerId;
        Customer customer = customerDao.findOne(customerId);
        TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        if (customer == null || customer.getAlive() == 0 || teaSaler == null || teaSaler.getAlive() == 0){
            return null;
        }
        OrderEn orderEn = new OrderEn();
        orderEn.setTel(createOrderModel.tel);
        orderEn.setZip(createOrderModel.zip);
        orderEn.setAddress(createOrderModel.address);
        orderEn.setCustomer(customer);
        orderEn.setTeaSaler(teaSaler);
        orderEn.setType(createOrderModel.type);
        orderEn.setCreateDate(new Date());
        orderEn.setAlive(1);
        //orderEn.set
        return orderEnDao.save(orderEn);
    }
}
