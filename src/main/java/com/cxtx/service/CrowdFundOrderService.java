package com.cxtx.service;

import com.cxtx.entity.CrowdFundOrder;
import com.cxtx.model.CreateCrowdFundOrderModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateOrderModel;
import org.springframework.data.domain.Page;

/**
 * Created by jinchuyang on 16/12/6.
 */
public interface CrowdFundOrderService {
    ServiceResult insertOrder(CreateCrowdFundOrderModel createCrowdFundOrderModel);

    Page<CrowdFundOrder> search(long customerId, long teaSalerId, long crowdFundingId, String teaSalerName, int state, int isSend, int isConfirm, int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address, String tel, String beginDateStr, String endDateStr, int pageIndex, int pageSize, String sortField, String sortOrder);

    ServiceResult payRemain(Long id);

    CrowdFundOrder confirmOrder(UpdateOrderModel updateOrderModel);

    CrowdFundOrder sendOrder(UpdateOrderModel updateOrderModel);

    ServiceResult cancelOrder(Long id);

    ServiceResult payUnFinished(Long id);
}
