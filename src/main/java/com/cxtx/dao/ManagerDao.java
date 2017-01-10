package com.cxtx.dao;

import com.cxtx.entity.Account;
import com.cxtx.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface ManagerDao extends JpaRepository<Manager,Long>{
    Manager findByAccountAndAlive(Account account, int alive);
    Manager findByIdAndAlive(Long id,int alive);
}
