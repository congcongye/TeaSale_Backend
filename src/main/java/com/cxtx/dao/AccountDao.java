package com.cxtx.dao;

import com.cxtx.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface AccountDao extends JpaRepository<Account, Long>{
    Account findByTelAndPasswordAndAlive(String tel, String password, int alive);

    Account findByTelAndAlive(String tel, int alive);
}
