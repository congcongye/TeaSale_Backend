package com.cxtx.service.impl;

import com.cxtx.dao.AccountDao;
import com.cxtx.dao.ManagerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateManagerModel;
import com.cxtx.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by jinchuyang on 16/10/19.
 */
@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private AccountDao accountDao;


    @Override
    public Manager findByAccountAndAlive(Account account) {
        return managerDao.findByAccountAndAlive(account, 1);
    }

    @Override
    public Manager addManager(CreateManagerModel createManagerModel, Account account) {
        if (createManagerModel == null || account == null) {
            return null;
        }
        Manager manager = new Manager();
        manager.setAlive(1);
        manager.setTel(manager.getTel());
        manager.setLevel(createManagerModel.getLevel());

        manager.setAccount(account);
        manager.setName(createManagerModel.getName());
        manager.setCreateDate(new Date());
        return managerDao.save(manager);
    }

    @Override
    public Manager update(CreateManagerModel createManagerModel) {
        Manager manager = null;
        if (createManagerModel.getTel()== null || "".equals(createManagerModel.getTel())){
            return  null;
        }
        Account account = accountDao.findByTelAndAlive(createManagerModel.getTel(),1);
        if (account!= null){
            account.setPassword(createManagerModel.getPassword());
            accountDao.save(account);
            manager = managerDao.findByAccountAndAlive(account, 1);
            manager.setName(createManagerModel.getName());
            manager = managerDao.save(manager);
        }
        return manager;
    }
}
