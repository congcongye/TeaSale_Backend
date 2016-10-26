package com.cxtx.dao;

import com.cxtx.entity.Account;
import com.cxtx.entity.TeaSeller;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface TeaSalerDao extends JpaRepository<TeaSeller, Long> {
    TeaSeller findByAccountAndAlive(Account account, int alive);
}
