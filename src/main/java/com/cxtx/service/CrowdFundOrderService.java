package com.cxtx.service;

import com.cxtx.entity.CrowdFundOrder;
import com.cxtx.model.CreateCrowdFundOrderModel;

/**
 * Created by jinchuyang on 16/12/6.
 */
public interface CrowdFundOrderService {
    CrowdFundOrder insertOrder(CreateCrowdFundOrderModel createCrowdFundOrderModel);
}
