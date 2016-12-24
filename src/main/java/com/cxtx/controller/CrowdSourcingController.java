package com.cxtx.controller;

import com.cxtx.dao.AccountDao;
import com.cxtx.dao.CrowdSourcingDao;
import com.cxtx.dao.CustomerDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.CrowdSourcing;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import com.cxtx.model.CrowdSourcingModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.impl.CrowdSourcingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ycc on 16/12/22.
 */
@Controller
public class CrowdSourcingController extends ApiController{
    @Autowired
    private CrowdSourcingImpl crowdSourcingImpl;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CrowdSourcingDao crowdSourcingDao;
    @Autowired
    private AccountDao accountDao;

    /**
     * 众包的新增
     * @param product_id
     * @param customer_id
     * @param crowdSourcingModel
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult newCrowdSourcing(@RequestParam(value = "product_id",defaultValue ="-1")Long product_id, @RequestParam(value = "customer_id",defaultValue ="-1")Long customer_id,
                                          @RequestBody CrowdSourcingModel crowdSourcingModel){
        Product product =productDao.findByIdAndAlive(product_id,1);
        Customer customer=customerDao.findByIdAndAlive(customer_id,1);
        if (product == null ) {
            return ServiceResult.fail(500, "no product record !");
        }
        if(customer == null){
            return ServiceResult.fail(500, "no customer record !");
        }
        /**
         * 消费者用户的钱小于发起众包所需要的所有钱
         */
        double totalMoney=crowdSourcingModel.totalNum*crowdSourcingModel.unitMoney;
        if(customer.getAccount().getMoney() < totalMoney){
            return ServiceResult.fail(500, "you don't have enough money !");
        }
        CrowdSourcing cd = new CrowdSourcing();
        cd.setProduct(product);
        cd.setCustomer(customer);
        cd.setEarnest(crowdSourcingModel.earnest);
        cd.setUnitMoney(crowdSourcingModel.unitMoney);
        cd.setUnitNum(crowdSourcingModel.unitNum);
        cd.setCreateDate(crowdSourcingModel.createDate);
        cd.setDealDate(crowdSourcingModel.dealDate);
        cd.setState(crowdSourcingModel.state);
        cd.setTotalNum(crowdSourcingModel.totalNum);
        cd.setRemainderNum(crowdSourcingModel.totalNum);
        cd.setDeliverDate(crowdSourcingModel.deliverDate);
        CrowdSourcing result = crowdSourcingImpl.newCrowdSourcing(cd);
        customer.getAccount().setMoney(totalMoney+customer.getAccount().getMoney());
        accountDao.save(customer.getAccount());//扣除相应金额
        return ServiceResult.success(result);
    }

    /**
     * 众包的修改
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateCrowdSourcing(@RequestParam (value = "id",defaultValue = "-1")Long id,@RequestBody CrowdSourcingModel model){
        CrowdSourcing cd =crowdSourcingDao.findByIdAndAlive(id,1);
        if (cd== null ) {
            return ServiceResult.fail(500, "no crowdSourcing record !");
        }
        if(crowdSourcingImpl.isWorking(cd)){
            return ServiceResult.fail(500, "crowdSourcing order have generated!");
        }
        cd.setEarnest(model.earnest);
        cd.setUnitMoney(model.unitMoney);
        cd.setUnitNum(model.unitNum);
        cd.setCreateDate(model.createDate);
        cd.setDealDate(model.dealDate);
        cd.setDeliverDate(model.deliverDate);
        cd.setState(model.state);
        cd.setTotalNum(model.totalNum);
        cd.setRemainderNum(model.totalNum);
        CrowdSourcing result = crowdSourcingDao.save(cd);
        return ServiceResult.success(result);
    }

    /**
     * 众包的查询
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult search(@RequestParam(value = "customer_id",defaultValue = "-1")Long customer_id,@RequestParam(value = "productName",defaultValue = "")String productName,
                                @RequestParam(value = "productType_id",defaultValue = "-1")Long productType_id,@RequestParam(value="state",defaultValue ="0")int state,
                                @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                @RequestParam(value="sortField", defaultValue="id") String sortField,
                                @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        Page<CrowdSourcing> result = crowdSourcingImpl.searchCrowdSourcing(customer_id,productName,productType_id,state,pageIndex,pageSize,sortField,sortOrder);
        return ServiceResult.success(result);
    }

    /**
     * 众包的删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ServiceResult delete(@RequestParam(value = "id",defaultValue = "-1") Long id){
        CrowdSourcing cs =crowdSourcingDao.findByIdAndAlive(id,1);
        if (cs == null ) {
            return ServiceResult.fail(500, "no crowdSourcing record !");
        }
        if(crowdSourcingImpl.isWorking(cs)){
            return ServiceResult.fail(500, "crowdSourcing order have generated!");
        }
        cs.setAlive(0);
        CrowdSourcing result = crowdSourcingDao.save(cs);
        return ServiceResult.success("all succeed");
    }

    /**
     * 按id查众包
     * @param id
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/getById", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getById(@RequestParam(value = "id",defaultValue = "-1")Long id){
        CrowdSourcing cs = crowdSourcingDao.findByIdAndAlive(id,1);
        return  ServiceResult.success(cs);
    }
}
