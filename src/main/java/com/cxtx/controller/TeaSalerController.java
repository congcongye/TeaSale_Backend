package com.cxtx.controller;

import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.entity.TeaSeller;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.CreateTeaSalerModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UserListCell;
import com.cxtx.service.AccountService;
import com.cxtx.service.CustomerService;
import com.cxtx.service.TeaSalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    //@RequestParam (value ="state",defaultValue = "1")int state
    @RequestMapping(value = "/teaSalers/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult search(@RequestParam(value = "name", defaultValue = "") String name,
                                @RequestParam(value = "level", defaultValue = "1")int level,
                                @RequestParam(value = "tel", defaultValue = "")String tel,
                                @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                @RequestParam(value="sortField", defaultValue="id") String sortField,
                                @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        Page<TeaSeller> result = teaSalerService.searchTeaSaler(name, level, tel, pageIndex, pageSize, sortField, sortOrder);
        return  ServiceResult.success(result);
    }
}