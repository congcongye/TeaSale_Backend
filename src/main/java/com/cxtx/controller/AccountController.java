package com.cxtx.controller;

import com.cxtx.dao.AccountDao;
import com.cxtx.entity.Account;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.AccountService;
import com.cxtx.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;

import static com.cxtx.utils.MD5Util.byteArrayToHexString;

/**
 * Created by jinchuyang on 16/11/12.
 */
@Controller
public class AccountController extends  ApiController{

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageService imageService;
    /**
     *
     * @param picture
     * @param accountId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/image/head/upload", method = RequestMethod.POST)//,produces = "text/plain;charset=UTF-8"
    @ResponseBody
    public ServiceResult uploadHeadPic(@RequestParam("picture") MultipartFile picture,
                                       @RequestParam(value = "accountId",defaultValue = "-1")Long accountId) throws IOException {//, HttpServletRequest request
        checkParameter(picture!=null,"图片不存在");
        Account account = accountService.findAliveAccount(accountId);
        checkParameter(account!=null,"该账号不存在");
        int result = imageService.uploadHeadPic(picture,account);
        if (result ==0){
            return ServiceResult.fail(500,"头像图片获取失败");
        }
        return ServiceResult.success("头像上传成功");
    }

    @RequestMapping(value = "/account/recharge", method = RequestMethod.GET)//,produces = "text/plain;charset=UTF-8"
    @ResponseBody
    public  ServiceResult recharge(@RequestParam("money") double money,
                                   @RequestParam("accountId") long accountId){
        checkParameter(accountId > 0 ,"账号id不正确");
        Account account = accountService.findAliveAccount(accountId);
        checkParameter(account!=null,"该账号不存在");
        account = accountService.recharge(money,accountId);
        if (account == null){
            return ServiceResult.fail(500,"充值失败");
        }
        return  ServiceResult.success("充值成功");
    }

}
