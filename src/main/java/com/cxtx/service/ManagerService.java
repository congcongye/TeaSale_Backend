package com.cxtx.service;

import com.cxtx.entity.Account;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateManagerModel;
import org.springframework.stereotype.Component;

/**
 * Created by jinchuyang on 16/10/19.
 */
public interface ManagerService {
    Manager findByAccountAndAlive(Account account);

    //Manager addManager(Manager manager);

    Manager addManager(CreateManagerModel createManagerModel, Account account);

    Manager update(CreateManagerModel createManagerModel);

    Manager getManager();

    void changeMoney(Manager manager, double money, int type);
}
