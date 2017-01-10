package com.cxtx.controller;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.CrowdSourcingModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.CrowdSourcingService;
import com.cxtx.service.ManagerService;
import com.cxtx.service.ProductService;
import com.cxtx.service.impl.CrowdSourcingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/12/22.
 */
@Controller
public class CrowdSourcingController extends ApiController{
    @Autowired
    private CrowdSourcingService crowdSourcingService;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CrowdSourcingDao crowdSourcingDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private ManagerService managerService;
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

        CrowdSourcing result = crowdSourcingService.newCrowdSourcing(cd);
        customer.getAccount().setMoney(customer.getAccount().getMoney()-totalMoney);
        accountDao.save(customer.getAccount());//扣除相应金额
        Manager systemManager =managerService.getManager();
        managerService.changeMoney(systemManager,totalMoney,0);
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
        if(crowdSourcingService.isWorking(cd)){
            return ServiceResult.fail(500, "crowdSourcing order have generated!");
        }
        double oldTotalMoney=cd.getUnitMoney()*cd.getTotalNum();
        cd.setEarnest(model.earnest);
        cd.setUnitMoney(model.unitMoney);
        cd.setUnitNum(model.unitNum);
        cd.setCreateDate(model.createDate);
        cd.setDealDate(model.dealDate);
        cd.setDeliverDate(model.deliverDate);
        cd.setState(model.state);
        cd.setTotalNum(model.totalNum);
        cd.setRemainderNum(model.totalNum);
        double totalMoney=model.unitMoney*model.totalNum;
        Customer customer=cd.getCustomer();
        Account account=customer.getAccount();
        account.setMoney(account.getMoney()+oldTotalMoney-totalMoney);
        accountDao.save(account);
        Manager systemManager =managerService.getManager();
        managerService.changeMoney(systemManager,oldTotalMoney-totalMoney,0);
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
                                @RequestParam(value = "productType_id",defaultValue = "-1")Long productType_id,@RequestParam(value="state",defaultValue ="-1")int state,
                                @RequestParam(value="pageIndex", defaultValue="0") int pageIndex,
                                @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                                @RequestParam(value="sortField", defaultValue="id") String sortField,
                                @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder){
        Page<CrowdSourcing> result = crowdSourcingService.searchCrowdSourcing(customer_id,productName,productType_id,state,pageIndex,pageSize,sortField,sortOrder);
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
        if(crowdSourcingService.isWorking(cs)){
            return ServiceResult.fail(500, "crowdSourcing order have generated!");
        }
        cs.setAlive(0);
        Account account =cs.getCustomer().getAccount();
        account.setMoney(account.getMoney()+cs.getUnitMoney()*cs.getTotalNum());
        accountDao.save(account);
        CrowdSourcing result = crowdSourcingDao.save(cs);
        Manager systemManager =managerService.getManager();
        managerService.changeMoney(systemManager,cs.getUnitMoney()*cs.getTotalNum(),1);
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

    /**
     * 茶产品的新增,product对象中必须传入的数据应该由前台来限制
     * @param product
     * @param productType_id
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/newProduct", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult newProduct(@RequestBody Product product, @RequestParam (value="productType_id",defaultValue = "-1")Long productType_id){
        checkParameter(product!=null,"product is empty");
        ProductType pt =productTypeDao.findByIdAndAlive(productType_id,1);
        checkParameter(pt!=null,"productType doesn't exist");
        Product result=null;
        product.setProductType(pt);
        product.setUrl(pt.url);
        product.setAlive(1);//存在
        product.setState(0);//未上架
        result = productService.newProduct(product);
        return ServiceResult.success(result);
    }

    /**
     * 获得众包的参与者(茶农)
     * @param crowdSourcingId
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/participant/{crowdSourcingId}", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult getParticipants(@PathVariable(value = "crowdSourcingId")long crowdSourcingId){
        checkParameter(crowdSourcingId > 0, "crowdSourcing id is invalid!");
        List<TeaSaler> teaSalers = crowdSourcingService.findParticipants(crowdSourcingId);
        if (teaSalers == null ){
            return ServiceResult.fail(500,"no such crowdFunding!");
        }
        return ServiceResult.success(teaSalers);
    }

    /**
     * 众包的手工确认
     * @param id
     * @return
     */
    @RequestMapping(value = "/crowdSourcing/confirm", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult Confirm(@RequestParam(value = "id",defaultValue = "-1")Long id){
        CrowdSourcing crowdSourcing=crowdSourcingDao.findByIdAndAlive(id,1);
        if(null==crowdSourcing||crowdSourcing.getState()!=0){
            return ServiceResult.fail(500,"众包不存在");
        }else {
            if(new Date().after(crowdSourcing.getCreateDate())){
                crowdSourcing.setState(3);
                CrowdSourcing result =crowdSourcingDao.save(crowdSourcing);
                return ServiceResult.success(result);
            }else{
                return ServiceResult.fail(500,"众包还未开始");
            }
        }
    }

}
