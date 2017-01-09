package com.cxtx.dao;

import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.CrowdSourcing;
import com.cxtx.entity.CrowdSourcingOrder;
import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by ycc on 16/12/22.
 */
public interface CrowdSourcingDao extends JpaRepository<CrowdSourcing, Long>, JpaSpecificationExecutor<CrowdSourcing> {
    CrowdSourcing findByIdAndAlive(Long id,int alive);
    List<CrowdSourcing> findByProductAndAlive(Product p, int alive);

    List<CrowdSourcing> findByAlive(int alive);
    List<CrowdSourcing> findByAliveAndState(int alive,int state);


}
