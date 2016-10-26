package com.cxtx.service.impl;

import com.cxtx.dao.CustomerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinchuyang on 16/10/26.
 */
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Override
    public Customer findByAccountAndAlive(Account account) {
        return customerDao.findByAccountAndAlive(account, 1);
    }

    @Override
    public Customer addCustomer(CreateCustomerModel createCustomerModel, Account account) {
        if (createCustomerModel == null || account == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setMoney(createCustomerModel.getMoney());
        customer.setTel(createCustomerModel.getTel());
        customer.setAccount(account);
        customer.setHeadUrl(createCustomerModel.getHeadUrl());
        customer.setAddress(createCustomerModel.getAddress());
        customer.setLever(createCustomerModel.getLever());
        customer.setNickname(createCustomerModel.getNickname());
        customer.setZip(createCustomerModel.getZip());
        customer.setAlive(1);
        return customerDao.save(customer);
    }
}
