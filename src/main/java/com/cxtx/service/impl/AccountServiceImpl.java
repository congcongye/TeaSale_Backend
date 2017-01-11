package com.cxtx.service.impl;

import com.cxtx.dao.AccountDao;
import com.cxtx.entity.Account;
import com.cxtx.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;

import static com.cxtx.utils.MD5Util.byteArrayToHexString;

/**
 * Created by jinchuyang on 16/10/26.
 */
@Service("AccountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountService accountService;

    @Override
    public Account register(String tel, String password, int label) {
        if (tel != null && password != null) {
            Account account = accountDao.findByTelAndAlive(tel, 1);
            if (account == null || account.getId() == 0){
                account = new Account();
                account.setTel(tel);
                String newPassword =accountService.MD5Encode(password);
                account.setPassword(newPassword);
                account.setAlive(1);
                account.setLabel(label);
                return accountDao.save(account);
            }
        }
        return null;
    }

    @Override
    public Account login(String tel, String password) {
        if (tel != null && password != null){
            return accountDao.findByTelAndPasswordAndAlive(tel, password, 1);

        }
        return null;
    }

    @Override
    public Account findAliveAccount(long accountId) {
        Account account = accountDao.findOne(accountId);
        if (account != null && account.getAlive() == 1){
            return account;
        }
        return null;
    }

    @Override
    public Account recharge(double money, long accountId) {
        Account account = accountDao.findOne(accountId);
        if (account != null && account.getAlive() == 1) {
            account.setMoney(account.getMoney()+money);
            return accountDao.save(account);
        }
        return null;
    }

    @Override
    public  String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
}
