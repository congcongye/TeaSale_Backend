package com.cxtx.controller;

import com.cxtx.dao.ManagerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateManagerModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.AccountService;
import com.cxtx.service.ImageService;
import com.cxtx.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by jinchuyang on 16/10/19.
 */
@Controller
public class ManagerController extends ApiController{
    @Autowired
    private ManagerService managerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private ManagerDao managerDao;

    /**
     *
     * @param account
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manager/login", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult login(@RequestBody Account account) throws Exception{
        checkParameter(account!=null,"manager cannot be empty!");
        checkParameter(account.getTel()!=null,"tel cannot be empty!");
        String newPassword =accountService.MD5Encode(account.getPassword());
        Account accountGet = accountService.login(account.getTel(),newPassword);
        if (accountGet == null){
            return ServiceResult.fail(500, "no account record !");
        }
        Manager manager = managerService.findByAccountAndAlive(accountGet);
        if (manager == null ) {
            return ServiceResult.fail(500, "no manager record !");
        }
        return ServiceResult.success(manager);
    }

    /**
     *
     * @param createManagerModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manager/register", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult register(@RequestBody CreateManagerModel createManagerModel) throws Exception{
        checkParameter(createManagerModel !=null,"manager cannot be empty!");
        Account account = accountService.register(createManagerModel.getTel(), createManagerModel.getPassword(),0);
        if (account == null){
            return ServiceResult.fail(500, "register failed, the tel already has account!");
        }
        //return ServiceResult.success(account);
        Manager manager = managerService.addManager(createManagerModel, account);
        if (manager == null){
            return ServiceResult.fail(500,"register fail");
        }
        return ServiceResult.success(manager);
    }

    /**
     *
     * @param createManagerModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/manager/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult update(@RequestBody CreateManagerModel createManagerModel) throws Exception{
        checkParameter(createManagerModel !=null,"manager cannot be empty!");
        //return ServiceResult.success(account);
        Manager manager = managerService.update(createManagerModel);
        if (manager == null){
            return ServiceResult.fail(500,"update fail");
        }
        return ServiceResult.success(manager);
    }

    @RequestMapping(value = "/manager/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult singularDetial(@PathVariable(value = "id") long id){
        checkParameter(id>0,"参数错误" + id);
        Manager manager  = managerDao.findByIdAndAlive(id,1);
        if (manager ==null){
            return ServiceResult.fail(500,"该管理员不存在");
        }
        return ServiceResult.success(manager);
    }


}
