package com.cxtx.service;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.entity.TeaSeller;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.CreateTeaSalerModel;
import com.cxtx.model.UserListCell;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface TeaSalerService {
    TeaSeller findByAccountAndAlive(Account account);

    TeaSeller addTeaSaler(CreateTeaSalerModel createTeaSalerModel, Account account);

    Page<TeaSeller> searchTeaSaler(String name, int level, String tel, int pageIndex, int pageSize, String sortField, String sortOrder);
}
