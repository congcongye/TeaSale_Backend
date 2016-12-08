package com.cxtx.controller;

import com.cxtx.dao.CrowdFundingDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.CrowdFundOrder;
import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.Product;
import com.cxtx.model.CreateCrowdFundOrderModel;
import com.cxtx.model.IdModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateCrowdFundingModel;
import com.cxtx.service.CrowdFundOrderService;
import com.cxtx.service.CrowdFundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CrowdFundOrderController extends ApiController{

    @Autowired
    private CrowdFundingService crowdFundingService;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CrowdFundingDao crowdFundingDao;
    @Autowired
    private CrowdFundOrderService crowdFundOrderService;

    /**
     * 新增众筹订单
     * @param createCrowdFundOrderModel
     * @return
     */
    @RequestMapping(value = "/crowdFundOrder/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult addOrder(@RequestBody CreateCrowdFundOrderModel createCrowdFundOrderModel){
        checkParameter(createCrowdFundOrderModel != null,"order can't be null");
        CrowdFundOrder crowdFundOrder =  crowdFundOrderService.insertOrder(createCrowdFundOrderModel);
        if (crowdFundOrder == null){
            return  ServiceResult.fail(500,"create order failed");
        }
        return ServiceResult.success(crowdFundOrder);
    }

    /**
     * 搜索众筹订单
     * @param customerId
     * @param teaSalerId
     * @param crowdFundingId
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
    @RequestMapping(value = "/crowdFundOrders/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult searchOrder(@RequestParam(value = "customerId", defaultValue = "-1")long customerId,
                                     @RequestParam(value = "teaSalerId", defaultValue = "-1")long teaSalerId,
                                     @RequestParam(value = "crowdFundingId", defaultValue = "-1")long crowdFundingId,
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
        Page<CrowdFundOrder> crowdFundOrders = crowdFundOrderService.search(customerId, teaSalerId, crowdFundingId, teaSalerName,state, isSend, isConfirm, customerDelete, adminDelete,
                salerDelete, Refund_state, name, address, tel, beginDateStr,endDateStr,pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(crowdFundOrders);
    }



}
