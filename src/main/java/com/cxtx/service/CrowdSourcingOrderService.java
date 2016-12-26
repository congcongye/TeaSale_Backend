package com.cxtx.service;

import com.cxtx.entity.CrowdFundOrder;
import com.cxtx.entity.CrowdSourcingOrder;
import com.cxtx.model.CreateCrowdFundOrderModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateOrderModel;
import com.cxtx.model.newCrowdSourcingOrderModel;
import org.springframework.data.domain.Page;

/**
 * Created by jinchuyang on 16/12/6.
 */
public interface CrowdSourcingOrderService {

    ServiceResult insert(newCrowdSourcingOrderModel model);

    CrowdSourcingOrder confirmOrder(UpdateOrderModel updateOrderModel);

    CrowdSourcingOrder sendOrder(UpdateOrderModel updateOrderModel);

    ServiceResult cancelOrder(Long id);

    Page<CrowdSourcingOrder> search(long customerId, long teaSalerId, long crowdSourcingId, String teaSalerName, int state, int isSend, int isConfirm, int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address, String tel, String beginDateStr, String endDateStr, int pageIndex, int pageSize, String sortField, String sortOrder);
}
