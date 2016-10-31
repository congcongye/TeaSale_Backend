package com.cxtx.service.impl;

import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.TeaSeller;
import com.cxtx.model.CreateTeaSalerModel;
import com.cxtx.service.TeaSalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinchuyang on 16/10/27.
 */
@Service("TeaSalerService")
public class TeaSalerServiceImpl implements TeaSalerService{
    @Autowired
    private TeaSalerDao teaSalerDao;

    @Override
    public TeaSeller findByAccountAndAlive(Account account) {
        return teaSalerDao.findByAccountAndAlive(account,1);
    }

    @Override
    public TeaSeller addTeaSaler(CreateTeaSalerModel createTeaSalerModel, Account account) {
        TeaSeller teaSeller = new TeaSeller();
        teaSeller.setAlive(1);
        teaSeller.setTel(createTeaSalerModel.getTel());
        teaSeller.setMoney(createTeaSalerModel.getMoney());
        teaSeller.setNickname(createTeaSalerModel.getNickname());
        teaSeller.setLevel(createTeaSalerModel.getLever());
        teaSeller.setAddress(createTeaSalerModel.getAddress());
        teaSeller.setAccount(account);
        teaSeller.setHeadUrl(createTeaSalerModel.getHeadUrl());
        teaSeller.setIdCard(createTeaSalerModel.getIdCard());
        teaSeller.setLicenseUrl(createTeaSalerModel.getLicenseUrl());
        teaSeller.setName(createTeaSalerModel.getName());
        teaSeller.setZip(createTeaSalerModel.getZip());
        return teaSalerDao.save(teaSeller);
    }

}
