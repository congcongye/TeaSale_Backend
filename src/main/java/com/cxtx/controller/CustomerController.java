package com.cxtx.controller;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.CreateManagerModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.AccountService;
import com.cxtx.service.CustomerService;
import com.cxtx.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jinchuyang on 16/10/19.
 */
@Controller
public class CustomerController extends ApiController{
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    /**
     *
     * @param account
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/customer/login", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult login(@RequestBody Account account) throws Exception{
        checkParameter(account!=null,"manager cannot be empty!");
        checkParameter(account.getTel()!=null,"tel cannot be empty!");
        Account accountGet = accountService.login(account.getTel(),account.getPassword());
        if (accountGet == null){
            return ServiceResult.fail(500, "no account record !");
        }
        Customer customer = customerService.findByAccountAndAlive(accountGet);
        if (customer == null ) {
            return ServiceResult.fail(500, "no manager record !");
        }
        return ServiceResult.success(customer);
    }

    /**
     *
     * @param createCustomerModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult register(@RequestBody CreateCustomerModel createCustomerModel) throws Exception{
        checkParameter(createCustomerModel !=null,"manager cannot be empty!");
        Account account = accountService.register(createCustomerModel.getTel(), createCustomerModel.getPassword());
        if (account == null){
            return ServiceResult.fail(500, "register failed, the tel already has account!");
        }
        Customer customer = customerService.addCustomer(createCustomerModel, account);
        return ServiceResult.success(customer);
    }


}
