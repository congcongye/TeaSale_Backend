package com.cxtx.dao;

import com.cxtx.entity.TeaSaler;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ycc on 16/10/24.
 */
public interface TeaSellerDao extends JpaRepository<TeaSaler,Long> {

    TeaSaler findByIdAndStateAndAlive(Long id, int state, int alive);
}
