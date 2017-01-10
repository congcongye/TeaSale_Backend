package com.cxtx.service;

import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.CrowdSourcing;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.IdModel;
import com.cxtx.model.UpdateCrowdFundingModel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by ycc on 16/11/26.
 */
public interface CrowdSourcingService {


    CrowdSourcing newCrowdSourcing(CrowdSourcing cd);

    boolean isWorking(CrowdSourcing cd);

    Page<CrowdSourcing> searchCrowdSourcing(Long customer_id, String productName, Long productType_id, int state, int pageIndex, int pageSize, String sortField, String sortOrder);

    void checkNum();

    List<TeaSaler> findParticipants(long crowdSourcingId);
    void checkIsFinish();
    void addCustomerMoney();
}
