package com.cxtx.service;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.entity.TeaSeller;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.CreateTeaSalerModel;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface TeaSalerService {
    TeaSeller findByAccountAndAlive(Account account);

    TeaSeller addTeaSaler(CreateTeaSalerModel createTeaSalerModel, Account account);
}
