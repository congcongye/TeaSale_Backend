package com.cxtx.service.impl;

import com.cxtx.ImageUtils;
import com.cxtx.dao.ManagerDao;
import com.cxtx.entity.Account;
import com.cxtx.entity.Manager;
import com.cxtx.model.CreateManagerModel;
import com.cxtx.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by jinchuyang on 16/10/19.
 */
@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerDao managerDao;


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
        manager.setMoney(createManagerModel.getMoney());
        manager.setTel(manager.getTel());
        manager.setLevel(createManagerModel.getLevel());

        manager.setAccount(account);
        manager.setName(createManagerModel.getName());
        manager.setCreateDate(new Date());

        //存头像
        String headContent = createManagerModel.getHeadUrl();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtc.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            //return null;
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("picPath");
        //File folder = new File(folderPath);
        String uuid = UUID.randomUUID().toString().replaceAll("-","");//让图片名字不同
        String imageUrl = folderPath + File.separator + uuid + ".jpg";
        boolean createHeadImageResult = ImageUtils.GenerateImage(headContent,imageUrl);
        if (!createHeadImageResult){
            imageUrl = folderPath + File.separator + "default.jpg";
        }
        manager.setHeadUrl(imageUrl);
        return managerDao.save(manager);
    }
}
