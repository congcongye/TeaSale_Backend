package com.cxtx.controller;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.model.CreateOrderItemModel;
import com.cxtx.model.CreateOrderModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.OrderItemService;
import com.cxtx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    /**
     * 新增订单
     * @param createOrderModel
     * @return
     */
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

    /**
     * 确认订单项
     * @param createOrderItemModels
     * @return
     */
    @RequestMapping(value = "/orderItems/add", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult insertOrderItems(@RequestBody List<CreateOrderItemModel> createOrderItemModels){
        checkParameter(createOrderItemModels != null && createOrderItemModels.size() != 0,"orderItem can't be null");
        //checkParameter(orderEnId > 0, "no order");
        List<OrderItem> orderItems =orderItemService.insertItems(createOrderItemModels);
        if (orderItems == null || orderItems.size() != createOrderItemModels.size()){
            return ServiceResult.fail(500, " 订单失败");
        }
        return ServiceResult.success(orderItems);
    }

    /**
     *
     * @param customerId
     * @param teaSalerId
     * @param state
     * @param isSend
     * @param isConfirm
     * @param isComment
     * @param type
     * @param customerDelete
     * @param adminDelete
     * @param salerDelete
     * @param Refund_state
     * @param name
     * @param address
     * @param tel
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @RequestMapping(value = "/orders/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult searchOrder(@RequestParam(value = "customerId", defaultValue = "-1")long customerId,
                                     @RequestParam(value = "teaSalerId", defaultValue = "-1")long teaSalerId,
                                     @RequestParam(value = "state", defaultValue = "-1")int state,
                                     @RequestParam(value = "isSend", defaultValue = "-1")int isSend,
                                     @RequestParam(value = "isConfirm", defaultValue = "-1")int isConfirm,
                                     @RequestParam(value = "isComment", defaultValue = "-1")int isComment,
                                     @RequestParam(value = "type", defaultValue = "-1")int type,
                                     @RequestParam(value = "customerDelete", defaultValue = "-1")int customerDelete,
                                     @RequestParam(value = "adminDelete", defaultValue = "-1")int adminDelete,
                                     @RequestParam(value = "salerDelete", defaultValue = "-1")int salerDelete,
                                     @RequestParam(value = "Refund_state", defaultValue = "-1")int Refund_state,
                                     @RequestParam(value = "name", defaultValue = "")String name,
                                     @RequestParam(value = "address", defaultValue = "")String address,
                                     @RequestParam(value = "tel", defaultValue =  "")String tel,
                                     @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                     @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                     @RequestParam(value="sortField", defaultValue="id") String sortField,
                                     @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        Page<OrderEn> orderEns = orderService.search(customerId, teaSalerId, state, isSend, isConfirm, isComment,type, customerDelete, adminDelete,
                salerDelete, Refund_state, name, address, tel, pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(orderEns);
    }


}
