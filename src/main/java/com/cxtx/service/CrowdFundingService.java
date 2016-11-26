package com.cxtx.service;

import com.cxtx.entity.CrowdFunding;
import com.cxtx.model.IdModel;

import java.util.List;

/**
 * Created by ycc on 16/11/26.
 */
public interface CrowdFundingService {

     CrowdFunding newCrowdFunding(CrowdFunding crowdFunding);
     CrowdFunding updateCrowdFunding(CrowdFunding model);
     int deleteCrowdFunding(List<IdModel> list);
     List<CrowdFunding> searchCrowdFunding(Long product_id, Long teaSaler_id, int type, double lowEarnest, double highEarnest, double lowUnitNum, double highUnitNum, double lowUnitMoney, double highUnitMoney, int state, double lowRemainderNum, double highRemainderNum);
}
