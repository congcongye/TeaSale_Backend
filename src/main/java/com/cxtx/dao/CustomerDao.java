package com.cxtx.dao;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface CustomerDao extends JpaRepository<Customer, Long> {

    Customer findByAccountAndAlive(Account account, int alive);
}
