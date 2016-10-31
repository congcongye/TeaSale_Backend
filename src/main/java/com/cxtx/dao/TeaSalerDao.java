package com.cxtx.dao;

import com.cxtx.entity.Account;
import com.cxtx.entity.TeaSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface TeaSalerDao extends JpaRepository<TeaSeller, Long> , JpaSpecificationExecutor<TeaSeller>{
    TeaSeller findByAccountAndAlive(Account account, int alive);

//    @Query("")
//    Page<TeaSeller> findByNameAndLeverAndTelAndAlive(String name, int lever, String tel, int i, PageRequest pageRequest);
}
