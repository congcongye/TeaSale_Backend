package com.cxtx.dao;


import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by ycc on 16/11/26.
 */
public interface CrowdFundingDao extends JpaRepository<CrowdFunding, Long>, JpaSpecificationExecutor<CrowdFunding> {
    CrowdFunding findByIdAndAlive(Long id,int alive);
    List<CrowdFunding> findByProductAndAlive(Product product, int alive);

    List<CrowdFunding> findByStateAndAlive(int state, int alive);
}
