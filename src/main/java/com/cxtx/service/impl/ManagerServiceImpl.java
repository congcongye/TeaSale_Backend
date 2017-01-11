package com.cxtx.service.impl;

import com.cxtx.dao.AccountDao;
import com.cxtx.dao.ManagerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateManagerModel;
import com.cxtx.service.AccountService;
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
    @Autowired
    private AccountService accountService;

    /**
     * 获取管理员根据帐号
     * @param account
     * @return
     */
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

    /**
     * 更新管理员
     * @param createManagerModel
     * @return
     */
    @Override
    public Manager update(CreateManagerModel createManagerModel) {
        Manager manager = null;
        if (createManagerModel.getTel()== null || "".equals(createManagerModel.getTel())){
            return  null;
        }
        Account account = accountDao.findByTelAndAlive(createManagerModel.getTel(),1);
        if (account!= null){
            if (createManagerModel.getPassword() != null && !"".equals(createManagerModel.getPassword())){
                String newPassword =accountService.MD5Encode(createManagerModel.getPassword());
                account.setPassword(newPassword);
                accountDao.save(account);
            }
            manager = managerDao.findByAccountAndAlive(account,1);
            if(manager!=null){
                manager.setName(createManagerModel.getName());
                manager = managerDao.save(manager);
            }else{
                return null;
            }
        }
        return manager;
    }

    /**
     * 获取管理员
     * @return
     */
    @Override
    public Manager getManager() {
        Account account = accountDao.findByTelAndAlive("13212716306",1);
        if (account != null && account.getAlive() == 1){
            return findByAccountAndAlive(account);
        }
        return null;
    }

    /**
     * 金额变动
     * @param manager
     * @param money
     * @param type 0:加钱,1 扣钱
     */
    @Override
    public void changeMoney(Manager manager, double money, int type) {
        Account account = manager.getAccount();
        if (type == 0){
            account.setMoney(account.getMoney() + money);
        }
        if (type == 1){
            account.setMoney(account.getMoney() - money);
        }
        accountDao.save(account);
    }
}
