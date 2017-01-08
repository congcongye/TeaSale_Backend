package com.cxtx.service;

import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.Customer;
import com.cxtx.model.IdModel;
import com.cxtx.model.UpdateCrowdFundingModel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ycc on 16/11/26.
 */
public interface CrowdFundingService {

     CrowdFunding newCrowdFunding(CrowdFunding crowdFunding);
     CrowdFunding updateCrowdFunding(UpdateCrowdFundingModel model);
     int deleteCrowdFunding(List<IdModel> list);
     Page<CrowdFunding> searchCrowdFunding(Long product_id, Long teaSaler_id, int type, double lowEarnest, double highEarnest, double lowUnitNum, double highUnitNum, double lowUnitMoney, double highUnitMoney, int state, double lowRemainderNum, double highRemainderNum, Long productType_id, String productType_name, String product_name, int pageIndex, int pageSize, String sortField, String sortOrder);

    void checkNum();

    CrowdFunding confirmCrowdFunding(Long id);

    List<CrowdFunding> commend();

    List<Customer> findParticipants(long crowdFundingId);

    void checkIsFinish();
}
