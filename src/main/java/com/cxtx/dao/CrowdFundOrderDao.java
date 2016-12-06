package com.cxtx.dao;


import com.cxtx.entity.CrowdFundOrder;
import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by jinchuyang on 07/12/26.
 */
public interface CrowdFundOrderDao extends JpaRepository<CrowdFundOrder, Long>, JpaSpecificationExecutor<CrowdFundOrder> {

}
