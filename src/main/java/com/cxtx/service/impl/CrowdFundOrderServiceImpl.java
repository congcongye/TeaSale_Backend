package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.CreateCrowdFundOrderModel;
import com.cxtx.service.CrowdFundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by jinchuyang on 16/12/6.
 */
@Service("CrowdFundOrderService")
public class CrowdFundOrderServiceImpl implements CrowdFundOrderService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TeaSalerDao teaSalerDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CrowdFundingDao crowdFundingDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private CrowdFundOrderDao crowdFundOrderDao;


    @Override
    public CrowdFundOrder insertOrder(CreateCrowdFundOrderModel createCrowdFundOrderModel) {
        Long customerId = createCrowdFundOrderModel.customerId;
        Long teaSalerId = createCrowdFundOrderModel.teaSalerId;
        Long crowdFundingId = createCrowdFundOrderModel.crowdFundingId;
        Customer customer = customerDao.findOne(customerId);
        TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        CrowdFunding crowdFunding = crowdFundingDao.findOne(crowdFundingId);
        if (customer == null || customer.getAlive() == 0 || teaSaler == null || teaSaler.getAlive() == 0 || crowdFunding == null || crowdFunding.getAlive() == 0){
            return null;
        }
        Product product = crowdFunding.getProduct();
        Account account = customer.getAccount();
        double totalMoney = 0;
        CrowdFundOrder crowdFundOrder = new CrowdFundOrder();
        crowdFundOrder.setTel(createCrowdFundOrderModel.tel);
        crowdFundOrder.setZip(createCrowdFundOrderModel.zip);
        crowdFundOrder.setAddress(createCrowdFundOrderModel.address);
        crowdFundOrder.setCustomer(customer);
        crowdFundOrder.setTeaSaler(teaSaler);
        crowdFundOrder.setName(createCrowdFundOrderModel.name);
        crowdFundOrder.setCreateDate(new Date());
        crowdFundOrder.setAlive(1);
        crowdFundOrder.setState(0);
        crowdFundOrder.setCrowdFunding(crowdFunding);
        crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
        if (crowdFunding.getType() == 0){//现付
            if (customer.getAccount().getMoney() >= createCrowdFundOrderModel.num * crowdFunding.getUnitMoney()
                    && crowdFunding.getRemainderNum() >= createCrowdFundOrderModel.num ){
                totalMoney += createCrowdFundOrderModel.num * crowdFunding.getUnitMoney();
                totalMoney += product.getIsFree()==1? 0:product.getPostage();
            }
        }
        if (crowdFunding.getType() == 1){//预付
            if (crowdFunding.getRemainderNum() >= createCrowdFundOrderModel.num
                    && customer.getAccount().getMoney() >= createCrowdFundOrderModel.num * crowdFunding.getEarnest()){
                totalMoney += createCrowdFundOrderModel.num * crowdFunding.getUnitMoney();
                totalMoney += product.getIsFree()==1? 0:product.getPostage();
            }
        }
        if (totalMoney > 0){
            account.setMoney(account.getMoney() - totalMoney);
            accountDao.save(account);
            crowdFundOrder.setState(2);
            crowdFundOrder.setTotalPrice(totalMoney);
            crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
            crowdFunding.setRemainderNum(crowdFunding.getRemainderNum() - createCrowdFundOrderModel.num);
            crowdFunding.setJoinNum(crowdFunding.getJoinNum() + 1);
            crowdFundingDao.save(crowdFunding);
        }

        return crowdFundOrder;
    }
}
