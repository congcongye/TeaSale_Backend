package com.cxtx.controller;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.model.CreateOrderItemModel;
import com.cxtx.model.CreateOrderModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.OrderItemService;
import com.cxtx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jinchuyang on 16/11/15.
 */
@Controller
public class OrderController extends ApiController{

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping(value = "/order/add", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult insertOrder(@RequestBody CreateOrderModel createOrderModel){
        checkParameter(createOrderModel != null, "order can't be empty");
        OrderEn orderEn = orderService.insertOrder(createOrderModel);
        if (orderEn == null) {
            return ServiceResult.fail(500, "insert failed!");
        }
        return ServiceResult.success(orderEn);
    }

    @RequestMapping(value = "/orderItems/add", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult insertOrderItems(@RequestBody List<CreateOrderItemModel> createOrderItemModels){
        checkParameter(createOrderItemModels != null && createOrderItemModels.size() != 0,"orderItem can't be null");
        List<OrderItem> list =orderItemService.insertItems(createOrderItemModels);
        if (list.size() != createOrderItemModels.size()){
            return ServiceResult.fail(500, "the num of succeed is "+list.size()+" ; the fail number is "+(createOrderItemModels.size()-list.size()));
        }
        return ServiceResult.success(list);
    }
}
