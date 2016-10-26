package com.cxtx.controller;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.entity.TeaSeller;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.CreateTeaSalerModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.AccountService;
import com.cxtx.service.CustomerService;
import com.cxtx.service.TeaSalerService;
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
public class TeaSalerController extends ApiController{
    @Autowired
    private TeaSalerService teaSalerService;

    @Autowired
    private AccountService accountService;

    /**
     *
     * @param account
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teaSaler/login", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult login(@RequestBody Account account) throws Exception{
        checkParameter(account!=null,"manager cannot be empty!");
        checkParameter(account.getTel()!=null,"tel cannot be empty!");
        Account accountGet = accountService.login(account.getTel(),account.getPassword());
        if (accountGet == null){
            return ServiceResult.fail(500, "no account record !");
        }
        TeaSeller teaSeller = teaSalerService.findByAccountAndAlive(accountGet);
        if (teaSeller == null ) {
            return ServiceResult.fail(500, "no manager record !");
        }
        return ServiceResult.success(teaSeller);
    }

    /**
     *
     * @param createTeaSalerModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teaSaler/register", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult register(@RequestBody CreateTeaSalerModel createTeaSalerModel) throws Exception{
        checkParameter(createTeaSalerModel !=null,"manager cannot be empty!");
        Account account = accountService.register(createTeaSalerModel.getTel(), createTeaSalerModel.getPassword());
        if (account == null){
            return ServiceResult.fail(500, "register failed, the tel already has account!");
        }
        TeaSeller teaSeller = teaSalerService.addTeaSaler(createTeaSalerModel, account);
        return ServiceResult.success(teaSeller);
    }
}
