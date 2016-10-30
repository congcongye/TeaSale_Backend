package com.cxtx.service;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.CreateManagerModel;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface CustomerService {
    Customer findByAccountAndAlive(Account account);


    Customer addCustomer(CreateCustomerModel createCustomerModel, Account account);
}
