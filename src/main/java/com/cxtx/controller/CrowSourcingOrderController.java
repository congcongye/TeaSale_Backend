package com.cxtx.controller;

import com.cxtx.entity.CrowdSourcingOrder;
import com.cxtx.model.IdModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateOrderModel;
import com.cxtx.model.newCrowdSourcingOrderModel;
import com.cxtx.service.CrowdSourcingOrderService;
import com.cxtx.service.impl.CrowdSourcingOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ycc on 16/12/24.
 */
@Controller
public class CrowSourcingOrderController extends ApiController{


    @Autowired
    private CrowdSourcingOrderService crowdSourcingOrderServiceImpl;

    /**
     * 众包订单的生成
     * @param model
     * @return
     */
    @RequestMapping(value = "/crowdSourcingOder/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult insertOrder(@RequestBody newCrowdSourcingOrderModel model){
        ServiceResult result= crowdSourcingOrderServiceImpl.insert(model);
        return result;
    }

    /**
     * 更新订单 确认发货与确认收货
     * @param updateOrderModel
     * @return
     */
    @RequestMapping(value = "/crowdSourcingOder/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateOrder(@RequestBody UpdateOrderModel updateOrderModel){
        checkParameter(updateOrderModel.orderId > 0,"invalid order!");
        CrowdSourcingOrder order = null;
        if (updateOrderModel.isConfirm == 1){
            order = crowdSourcingOrderServiceImpl.confirmOrder(updateOrderModel);//把钱加到茶农的账户中
        }
        if (updateOrderModel.isSend == 1){
            order = crowdSourcingOrderServiceImpl.sendOrder(updateOrderModel);
        }
        if (order == null ){
            return  ServiceResult.fail(500, "update fail");
        }
        return ServiceResult.success(order);
    }

    /**
     * 取消订单
     * @param idModel
     * @return
     */
    @RequestMapping(value = "/crowdSourcingOder/cancel", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult cancelOrder(@RequestBody IdModel idModel) {
        checkParameter(idModel.id > 0, "invalid order!");
        ServiceResult result = crowdSourcingOrderServiceImpl.cancelOrder(idModel.id);
        return result;
    }

    /**
     * 搜索众包订单
     * @param customerId
     * @param teaSalerId
     * @param crowdSourcingId
     * @param teaSalerName
     * @param state
     * @param isSend
     * @param isConfirm
     * @param customerDelete
     * @param adminDelete
     * @param salerDelete
     * @param Refund_state
     * @param name
     * @param address
     * @param tel
     * @param beginDateStr
     * @param endDateStr
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @RequestMapping(value = "/crowdSourcingOder/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult searchOrder(@RequestParam(value = "customerId", defaultValue = "-1")long customerId,
                                     @RequestParam(value = "teaSalerId", defaultValue = "-1")long teaSalerId,
                                     @RequestParam(value = "crowdSourcingId", defaultValue = "-1")long crowdSourcingId,
                                     @RequestParam(value = "teaSalerName", defaultValue = "")String teaSalerName,
                                     @RequestParam(value = "state", defaultValue = "-1")int state,
                                     @RequestParam(value = "isSend", defaultValue = "-1")int isSend,
                                     @RequestParam(value = "isConfirm", defaultValue = "-1")int isConfirm,
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
        Page<CrowdSourcingOrder> crowdFundOrders = crowdSourcingOrderServiceImpl.search(customerId, teaSalerId, crowdSourcingId, teaSalerName,state, isSend, isConfirm, customerDelete, adminDelete,
                salerDelete, Refund_state, name, address, tel, beginDateStr,endDateStr,pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(crowdFundOrders);
    }

}
