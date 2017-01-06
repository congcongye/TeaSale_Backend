package com.cxtx.dao;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface CustomerDao extends JpaRepository<Customer, Long> ,JpaSpecificationExecutor<Customer>{

    Customer findByAccountAndAlive(Account account, int alive);
    Customer findByIdAndAlive(Long id,int alive);
    List<Customer> findByAlive(int alive);
}
