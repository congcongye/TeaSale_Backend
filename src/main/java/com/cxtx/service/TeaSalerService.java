package com.cxtx.service;

import com.cxtx.entity.Account;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.CreateTeaSalerModel;
import org.springframework.data.domain.Page;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface TeaSalerService {
    TeaSaler findByAccountAndAlive(Account account);

    TeaSaler addTeaSaler(CreateTeaSalerModel createTeaSalerModel, Account account);

    Page<TeaSaler> searchTeaSaler(String name, int level, String tel, int state, int pageIndex, int pageSize, String sortField, String sortOrder);

    TeaSaler findById(long teaSalerId);
}
