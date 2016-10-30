package com.cxtx.service;

import com.cxtx.entity.Account;

/**
 * Created by jinchuyang on 16/10/26.
 */
public interface AccountService {

    Account register(String tel, String password);

    Account login(String tel, String password);
}
