package com.cxtx.dao;

import com.cxtx.entity.CrowdSourcing;
import com.cxtx.entity.CrowdSourcingOrder;
import com.cxtx.entity.TeaSaler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by ycc on 16/12/24.
 */
public interface CrowdSourcingOrderDao extends JpaRepository<CrowdSourcingOrder, Long>, JpaSpecificationExecutor<CrowdSourcingOrder> {
    CrowdSourcingOrder findByIdAndAlive(Long id,int alive);

    List<CrowdSourcingOrder> findByTeaSalerAndAlive(TeaSaler teaSaler, int alive);

    List<CrowdSourcingOrder> findByCrowdSourcingAndAlive(CrowdSourcing crowdSourcing, int alive);

}
