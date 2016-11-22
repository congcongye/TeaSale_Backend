package com.cxtx.dao;

import com.cxtx.entity.Account;
import com.cxtx.entity.TeaSaler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface TeaSalerDao extends JpaRepository<TeaSaler, Long> , JpaSpecificationExecutor<TeaSaler>{
    TeaSaler findByAccountAndAlive(Account account, int alive);

    TeaSaler findByIdAndStateAndAlive(Long id, int state, int alive);

    @Query("SELECT t FROM TeaSaler t WHERE t.alive = :alive AND t.name like :teaSalerName")
    List<TeaSaler> findByNameAndAlive(@Param("teaSalerName") String teaSalerName, @Param("alive") int alive);

//    @Query("")
//    Page<TeaSaler> findByNameAndLeverAndTelAndAlive(String name, int lever, String tel, int i, PageRequest pageRequest);
}
