package com.cxtx.service;

import com.cxtx.entity.Account;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface AccountService {

    Account register(String tel, String password, int label);

    Account login(String tel, String password);

    Account findAliveAccount(long accountId);

    Account recharge(double money, long accountId);
    String MD5Encode(String origin);
}
