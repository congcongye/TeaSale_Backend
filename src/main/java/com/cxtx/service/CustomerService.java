package com.cxtx.service;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.CreateManagerModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdatePasswordModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface CustomerService {
    Customer findByAccountAndAlive(Account account);


    Customer addCustomer(CreateCustomerModel createCustomerModel, Account account);

    Page<Customer> searchCustomer(String name, int level, String tel, int pageIndex, int pageSize, String sortField, String sortOrder);

    Customer findById(long customerId);

    Customer updateCustomer(CreateCustomerModel createCustomerModel);

    ServiceResult updatePassword(UpdatePasswordModel updatePasswordModel);
}
