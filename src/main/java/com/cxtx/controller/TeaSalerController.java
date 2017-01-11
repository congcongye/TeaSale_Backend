package com.cxtx.controller;

import com.cxtx.entity.Account;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.CreateTeaSalerModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.AccountService;
import com.cxtx.service.TeaSalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 茶农登陆
     * @param account
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @RequestMapping(value = "/teaSaler/login", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult login(@RequestBody Account account) throws Exception{
        checkParameter(account!=null,"茶农的账号不存在!");
        checkParameter(account.getTel()!=null,"电话号码为空!");
        String newPassword=accountService.MD5Encode(account.getPassword());
        Account accountGet = accountService.login(account.getTel(),newPassword);
        if (accountGet == null){
            return ServiceResult.fail(500, "该账号不存在!");
        }
        TeaSaler teaSaler = teaSalerService.findByAccountAndAlive(accountGet);
        if (teaSaler == null ) {
            return ServiceResult.fail(500, "该茶农不存在!");
        }
        if(teaSaler.getState()==0){
            return ServiceResult.fail(500, "暂未审核,请等待");
        }
        if(teaSaler.getState()==2){
           return ServiceResult.fail(500, "审核不通过");
        }
        return ServiceResult.success(teaSaler);
    }

    /**
     * 茶农注册
     * @param createTeaSalerModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teaSaler/register", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult register(@RequestBody CreateTeaSalerModel createTeaSalerModel) throws Exception{
        checkParameter(createTeaSalerModel !=null,"manager cannot be empty!");
        Account account = accountService.register(createTeaSalerModel.getTel(), createTeaSalerModel.getPassword(), 1);
        if (account == null){
            return ServiceResult.fail(500, "电话号码已经被注册");
        }
        TeaSaler teaSaler = teaSalerService.addTeaSaler(createTeaSalerModel, account);
        return ServiceResult.success(teaSaler);
    }


    /**
     * 搜索茶农
     * @param name
     * @param level
     * @param tel
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @RequestMapping(value = "/teaSalers/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult search(@RequestParam(value = "name", defaultValue = "") String name,
                                @RequestParam(value = "level", defaultValue = "-1")int level,
                                @RequestParam(value = "tel", defaultValue = "")String tel,
                                @RequestParam(value = "state", defaultValue = "-1")int state,
                                @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                @RequestParam(value="sortField", defaultValue="id") String sortField,
                                @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        Page<TeaSaler> result = teaSalerService.searchTeaSaler(name, level, tel,state, pageIndex, pageSize, sortField, sortOrder);
        return  ServiceResult.success(result);
    }

    /**
     * 搜索单个茶农
     * @param teaSalerId
     * @return
     */
    @RequestMapping(value = "/teaSaler/{teaSalerId}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult singularDetial(@PathVariable(value = "teaSalerId") long teaSalerId){
        checkParameter(teaSalerId>0,"Invalid TeaSalerId " + teaSalerId);
        TeaSaler teaSaler  = teaSalerService.findById(teaSalerId);
        if (teaSaler ==null){
            return ServiceResult.fail(500,"no teaSaler found!");
        }
        return ServiceResult.success(teaSaler);
    }

    /**
     * 茶农审核
     * @param teaSalers
     * @return
     */
    @RequestMapping(value = "/teaSalers/approve", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult approveTeasaler(@RequestBody List<TeaSaler> teaSalers){
        checkParameter(teaSalers!=null&&!teaSalers.isEmpty(),"teaSaler are empty");
        int succCount = teaSalerService.approveTeaSalers(teaSalers);
        if(succCount!=teaSalers.size()){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(teaSalers.size()-succCount));
        }
        return ServiceResult.success("all succeed");
    }

    /**
     * 更新茶农信息
     * @param teaSaler
     * @param teaSalerId
     * @return
     */
    @RequestMapping(value = "/teaSaler/update/{teaSalerId}", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateTeasaler(@RequestBody TeaSaler teaSaler,
                                        @PathVariable (value = "teaSalerId") long teaSalerId){
        checkParameter(teaSalerId > 0,"teaSaler is empty");
        teaSaler = teaSalerService.updateTeaSaler(teaSalerId,teaSaler);
        if(teaSaler == null){
            return ServiceResult.fail(500, "update teaSaler fail!");
        }
        return ServiceResult.success(teaSaler);
    }

    @RequestMapping(value = "/teaSaler/delete/{teaSalerId}", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult deleteTeaSaler(@PathVariable (value = "teaSalerId") long teaSalerId){
        checkParameter(teaSalerId > 0,"teaSaler is empty");
        return teaSalerService.deleteTeaSaler(teaSalerId);
    }
}
