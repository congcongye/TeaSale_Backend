package com.cxtx.controller;

import com.cxtx.dao.AccountDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Customer;
import com.cxtx.model.CreateCustomerModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdatePasswordModel;
import com.cxtx.service.AccountService;
import com.cxtx.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jinchuyang on 16/10/19.
 */
@Controller
public class CustomerController extends ApiController{
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountDao accountDao;


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
        String newPassword =accountService.MD5Encode(account.getPassword());
        Account accountGet = accountService.login(account.getTel(),newPassword);
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
        checkParameter(createCustomerModel !=null,"customer cannot be empty!");
        Account account = accountService.register(createCustomerModel.getTel(), createCustomerModel.getPassword(), 2);
        if (account == null){
            return ServiceResult.fail(500, "register failed, the tel already has account!");
        }
        Customer customer = customerService.addCustomer(createCustomerModel, account);
        return ServiceResult.success(customer);
    }

    /**
     *
     * @param name
     * @param level
     * @param tel
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @RequestMapping(value = "/customers/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult search(@RequestParam(value = "name", defaultValue = "") String name,
                                @RequestParam(value = "level", defaultValue = "-1")int level,
                                @RequestParam(value = "tel", defaultValue = "")String tel,
                                @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                @RequestParam(value="sortField", defaultValue="id") String sortField,
                                @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder) {
        Page<Customer> result = customerService.searchCustomer(name, level, tel, pageIndex, pageSize, sortField, sortOrder);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult singularDetial(@PathVariable(value = "customerId") long customerId){
        checkParameter(customerId>0,"Invalid TeaSalerId " + customerId);
        Customer customer  = customerService.findById(customerId);
        if (customer ==null){
            return ServiceResult.fail(500,"no teaSaler found!");
        }
        return ServiceResult.success(customer);
    }

    /**
     *
     * @param createCustomerModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/customer/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult update(@RequestBody CreateCustomerModel createCustomerModel) throws Exception{
        checkParameter(createCustomerModel !=null,"customer cannot be empty!");
        Customer customer = customerService.updateCustomer(createCustomerModel);
        Account account = accountDao.findByTelAndAlive(createCustomerModel.getTel(),1);
        if (account == null){
            return ServiceResult.fail(500, "没有该帐号");
        }
        if (customer == null){
            return ServiceResult.fail(500, "修改失败");
        }
        return ServiceResult.success(customer);
    }

    /**
     * 修改密码
     * @param updatePasswordModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/customer/updatePassword", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updatePassword(@RequestBody UpdatePasswordModel updatePasswordModel) throws Exception{
        checkParameter(updatePasswordModel !=null,"cannot be empty!");
        ServiceResult result = customerService.updatePassword(updatePasswordModel);
        return result;
    }

}
