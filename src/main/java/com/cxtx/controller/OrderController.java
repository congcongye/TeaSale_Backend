package com.cxtx.controller;

import com.cxtx.entity.OrderEn;
import com.cxtx.entity.OrderItem;
import com.cxtx.model.*;
import com.cxtx.service.OrderItemService;
import com.cxtx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     *
     * @param createOrderModels
     * @return
     */
    @RequestMapping(value = "/orders/add", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult insertOrders(@RequestBody List<CreateOrderModel> createOrderModels){
        checkParameter(createOrderModels != null && createOrderModels.size()!=0, "order can't be empty");
        List<OrderEn> orderEns = orderService.insertOrders(createOrderModels);
        if (orderEns == null || orderEns.size() != createOrderModels.size()) {
            return ServiceResult.fail(500, "形成订单失败,成功"+orderEns.size() +"个,失败"+(createOrderModels.size()-orderEns.size())+"个");
        }
        return ServiceResult.success(orderEns);
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
                                     @RequestParam(value = "teaSalerName", defaultValue = "")String teaSalerName,
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
                                     @RequestParam(value = "beginDateStr", defaultValue =  "")String beginDateStr,
                                     @RequestParam(value = "endDateStr", defaultValue =  "")String endDateStr,
                                     @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                     @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                     @RequestParam(value="sortField", defaultValue="id") String sortField,
                                     @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        Page<OrderEn> orderEns = orderService.search(customerId, teaSalerId, teaSalerName,state, isSend, isConfirm, isComment,type, customerDelete, adminDelete,
                salerDelete, Refund_state, name, address, tel, beginDateStr,endDateStr,pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(orderEns);
    }


    /**
     * 查询某一订单对应的订单项
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/orderItems/search/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult searchOrderItems(@PathVariable(value = "orderId")long orderId){
        checkParameter(orderId > 0,"no such order!");
        //checkParameter(orderEnId > 0, "no order");
        List<OrderItem> orderItems =orderItemService.searchItemsByOrder(orderId);
        if (orderItems == null || orderItems.size() == 0){
            return ServiceResult.fail(500, " 订单查询失败");
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
    @RequestMapping(value = "/orders/orderItems/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult searchOrders(@RequestParam(value = "customerId", defaultValue = "-1")long customerId,
                                     @RequestParam(value = "teaSalerId", defaultValue = "-1")long teaSalerId,
                                     @RequestParam(value = "teaSalerName", defaultValue = "")String teaSalerName,
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
                                     @RequestParam(value = "beginDateStr", defaultValue =  "")String beginDateStr,
                                     @RequestParam(value = "endDateStr", defaultValue =  "")String endDateStr,
                                     @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                     @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                     @RequestParam(value="sortField", defaultValue="id") String sortField,
                                     @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        Page<OrderEn> orderEns = orderService.search(customerId, teaSalerId, teaSalerName,state, isSend, isConfirm, isComment,type, customerDelete, adminDelete,
                salerDelete, Refund_state, name, address, tel, beginDateStr,endDateStr,pageIndex, pageSize, sortField, sortOrder);

        List<GetOrderModel> orderModels = new ArrayList<GetOrderModel>();
        for (OrderEn orderEn : orderEns) {
            GetOrderModel orderModel = new GetOrderModel();
            orderModel.orderEn = orderEn;
            orderModel.orderItems = orderItemService.searchItemsByOrder(orderEn.getId());
            orderModels.add(orderModel);
        }
        PageListModel pageListModel = PageListModel.Builder().pageIndex(pageIndex).pageSize(pageSize).
                totalCount(orderEns.getTotalElements()).totalPage(orderEns.getTotalPages()).list(orderModels).build();
        return ServiceResult.success(pageListModel);
    }

    /**
     * 更新订单 确认发货与确认收货
     * @param updateOrderModel
     * @return
     */
    @RequestMapping(value = "/order/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateOrder(@RequestBody UpdateOrderModel updateOrderModel){
        checkParameter(updateOrderModel.orderId > 0,"invaild order!");
        OrderEn orderEn = null;
        if (updateOrderModel.isConfirm == 1){
            orderEn = orderService.confirmOrder(updateOrderModel);
        }
        if (updateOrderModel.isSend == 1){
            orderEn = orderService.sendOrder(updateOrderModel);
        }
        if (orderEn == null ){
            return  ServiceResult.fail(500, "update fail");
        }
        return ServiceResult.success(orderEn);
    }
}
