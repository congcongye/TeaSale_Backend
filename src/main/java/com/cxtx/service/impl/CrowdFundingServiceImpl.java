package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.IdModel;
import com.cxtx.model.UpdateCrowdFundingModel;
import com.cxtx.service.CrowdFundOrderService;
import com.cxtx.service.CrowdFundingService;
import com.cxtx.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.*;

/**
 * Created by ycc on 16/11/26.
 */
@Service("CrowdFundingService")
public class CrowdFundingServiceImpl implements CrowdFundingService, SmartLifecycle{

    @Autowired
    private CrowdFundingDao crowdFundingDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private TeaSalerDao teaSalerDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private  CrowdFundOrderDao crowdFundOrderDao;
    @Autowired
    private CrowdFundOrderServiceImpl crowdFundOrderService;


    /**
     * 发起众筹,点击发起众筹前,先需要更商品类型和状态
     * @param crowdFunding
     * @return
     */
    @Override
    public CrowdFunding newCrowdFunding(CrowdFunding crowdFunding){
        CrowdFunding result=null;
        if(isUnique(crowdFunding)){
            Product p = crowdFunding.getProduct();
            p.setState(1);
            p.setCreateDate(new Date());//填入上架时间
            p.setType(1);//众筹
            Product newProduct = productDao.save(p);
            crowdFunding.setProduct(newProduct);
            crowdFunding.setCreateDate(new Date());
            crowdFunding.setRemainderNum(crowdFunding.getTotalNum());
            result=crowdFundingDao.save(crowdFunding);
          }
          return result;
    }

    /**
     * 众筹的修改,判断时候已经有订单产生,没有才能修改
     * @param model
     * @return
     */
    @Override
    public CrowdFunding updateCrowdFunding(UpdateCrowdFundingModel model){

            CrowdFunding oldCrowdFunding =crowdFundingDao.findByIdAndAlive(model.id,1);
            oldCrowdFunding.setType(model.type);
            oldCrowdFunding.setUnitMoney(model.unitMoney);
            oldCrowdFunding.setUnitNum(model.unitNum);
            oldCrowdFunding.setDealDate(model.dealDate);
            oldCrowdFunding.setDeliverDate(model.deliverDate);
            if(model.type==1){ //预售才需要修改交付剩余金钱的时间
                oldCrowdFunding.setPayDate(model.payDate);
                oldCrowdFunding.setEarnest(model.earnest);
            }
            oldCrowdFunding.setTotalNum(model.totalNum);
            oldCrowdFunding.setRemainderNum(model.totalNum);
            oldCrowdFunding.setJoinNum(model.joinNum);
            CrowdFunding result=null;
            if(isWorking(oldCrowdFunding)==false){
                result=crowdFundingDao.save(oldCrowdFunding);
            }
            return result;
    }

    /**
     * 众筹的删除
     * @param list
     * @return
     */
    public int deleteCrowdFunding(List<IdModel> list){
        int succCount = 0;
        for(IdModel idmodel:list){
            CrowdFunding cd =crowdFundingDao.findByIdAndAlive(idmodel.id,1);
            if(isWorking(cd)==false){
                cd.setAlive(0);
                crowdFundingDao.save(cd);
                succCount++;
            }
        }
        return succCount;
    }

    /**
     * 众筹的条件查询
     * @param product_id
     * @param teaSaler_id
     * @param type
     * @param lowEarnest
     * @param highEarnest
     * @param lowUnitNum
     * @param highUnitNum
     * @param lowUnitMoney
     * @param highUnitMoney
     * @param state
     * @param lowRemainderNum
     * @param highRemainderNum
     * @return
     */
    @Override
    public Page<CrowdFunding> searchCrowdFunding(Long product_id, Long teaSaler_id, int type, double lowEarnest, double highEarnest, double lowUnitNum, double highUnitNum, double lowUnitMoney, double highUnitMoney, int state, double lowRemainderNum, double highRemainderNum,Long productType_id,String productType_name,String product_name, int pageIndex, int pageSize, String sortField, String sortOrder){
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.toUpperCase().equals("DESC")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortField);
        Specification<CrowdFunding> specification = this.buildSpecifications(product_id, teaSaler_id, type, lowEarnest, highEarnest, lowUnitNum, highUnitNum, lowUnitMoney, highUnitMoney, state, lowRemainderNum, highRemainderNum,productType_id,productType_name,product_name);
        return  crowdFundingDao.findAll(Specifications.where(specification), new PageRequest(pageIndex, pageSize, sort));
    }

    private Specification<CrowdFunding> buildSpecifications(Long product_id,Long teaSaler_id,int type,double lowEarnest,double highEarnest,double lowUnitNum,double highUnitNum,double lowUnitMoney,double highUnitMoney,int state,double lowRemainderNum,double highRemainderNum,Long productType_id,String productType_name,String product_name) {
        final Product product =productDao.findByIdAndAlive(product_id,1);
        final TeaSaler teaSaler =teaSalerDao.findByIdAndStateAndAlive(teaSaler_id,1,1);
        final int ftype=type;
        final double flowEarnest =lowEarnest;
        final double fhighEarnest =highEarnest;
        final double flowUnitNum=lowUnitNum;
        final double fhighUnitNum=highUnitNum;
        final double flowUnitMoney=lowUnitMoney;
        final double fhighUnitMoney=highUnitMoney;
        final double fstate=state;
        final double flowRemainderNum=lowRemainderNum;
        final double fhighRemainderNum=highRemainderNum;
        final ProductType productType =productTypeDao.findByIdAndAlive(productType_id,1);
        final String fproduct_name =product_name;
        final String fproductType_name =productType_name;
           Specification<CrowdFunding> specification = new Specification<CrowdFunding>() {
            @Override
            public Predicate toPredicate(Root<CrowdFunding> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(null!=product){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Product>get("product"),product));
                }
                if(null!=teaSaler){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Product>get("product").get("teaSaler"),teaSaler));
                }
                if(null!=productType){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Product>get("product").get("productType"),productType));
                }
                if(ftype>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"),ftype));
                }
                if(flowEarnest>-1){
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("earnest"),flowEarnest));
                }
                if(fhighEarnest>-1){
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("earnest"),fhighEarnest));
                }
                if(flowUnitNum>-1){
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("unitNum"),flowUnitNum));
                }
                if(fhighUnitNum>-1){
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("unitNum"),fhighUnitNum));
                }
                if(flowUnitMoney>-1){
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("unitMoney"),flowUnitMoney));
                }
                if(fhighUnitMoney>-1){
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("unitMoney"),fhighUnitMoney));
                }
                if(fstate>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"),fstate));
                }
                if(flowRemainderNum>-1){
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("remainderNum"),flowRemainderNum));
                }
                if(fhighRemainderNum>-1){
                    predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("remainderNum"),fhighRemainderNum));
                }
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));
                predicate.getExpressions().add(criteriaBuilder.like(root.<Product>get("product").get("productType").get("name"),"%"+fproductType_name+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<Product>get("product").get("name"),"%"+fproduct_name+"%"));
                return criteriaBuilder.and(predicate);
            }
        };
        return specification;
    }


    /**
     * 判断当前众筹是否产生了订单
     * @param crowdFunding
     * @return
     */
    private boolean isWorking(CrowdFunding crowdFunding){
        boolean flag=false;
        List<CrowdFundOrder> list =crowdFundOrderDao.findByCrowdFundingAndAlive(crowdFunding,1);
        if(list!=null){
           for(CrowdFundOrder crowdFundOrder:list) {
               if(crowdFundOrder != null && crowdFundOrder.getAlive() == 1){
                   flag =true;
                   return flag;
               }
           }
        }
        return flag;
    }

    private boolean isUnique(CrowdFunding crowdFunding){
        List<CrowdFunding> list= crowdFundingDao.findByProductAndAlive(crowdFunding.getProduct(),1);
        if(null==list || list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 定时任务
     */
    @Override
    public void checkNum() {
        List<CrowdFunding> oldcrowdFundingList = crowdFundingDao.findByStateAndAlive(0,1);
        List<CrowdFunding> newCrowdFundingList = new ArrayList<CrowdFunding>();
        for (CrowdFunding crowdFunding : oldcrowdFundingList){
            Date dealDate = crowdFunding.getDealDate();
            Date now = new Date();
            if (now.after(dealDate)){
                System.out.print("现在时间: "+now+" 截止时间:  "+dealDate);
                if (crowdFunding.getRemainderNum() <= 0){
                    if (crowdFunding.getType()==1){
                        crowdFunding.setState(3);
                    }else {
                        crowdFunding.setState(4);
                    }
                }else{
                    crowdFunding.setState(2);
                    crowdFundOrderService.cancelOrdersByCrowdFund(crowdFunding);
                }
            }
            newCrowdFundingList.add(crowdFunding);
        }
        crowdFundingDao.save(newCrowdFundingList);
    }

    /**
     * 确认众筹形成,等待发货
     * @param id
     * @return
     */
    @Override
    public CrowdFunding confirmCrowdFunding(Long id) {
        CrowdFunding crowdFunding = crowdFundingDao.findByIdAndAlive(id, 1);
        if (crowdFunding == null || crowdFunding.getAlive() == 0) {
            return null;
        }
        if (crowdFunding.getType()==1){
            crowdFunding.setState(3);
        }else {
            crowdFunding.setState(4);
        }
        return  crowdFundingDao.save(crowdFunding);
    }


    @Override
    public List<CrowdFunding> commend() {
        List<CrowdFunding> crowdFundings = crowdFundingDao.findByStateAndAlive(0, 1);
        Map<CrowdFunding, Double> saleNum = new HashMap<CrowdFunding, Double>();
        for (CrowdFunding crowdFunding : crowdFundings) {//init
            saleNum.put(crowdFunding,0d);
        }
        List<CrowdFundOrder> crowdFundOrders = crowdFundOrderDao.findByAlive(1);
        for (CrowdFundOrder crowdFundOrder: crowdFundOrders){
            CrowdFunding crowdFunding = crowdFundOrder.getCrowdFunding();
            if (crowdFunding != null && crowdFunding.getAlive() == 1 && crowdFundings.contains(crowdFunding)){
                double num = saleNum.get(crowdFunding);
                num += crowdFundOrder.getNum();
                saleNum.put(crowdFunding,num);
            }

        }
        crowdFundings = new ArrayList<CrowdFunding>();
        Map<CrowdFunding, Double> result = MapUtil.sortByValueDESC(saleNum);
        for (CrowdFunding crowdFunding : result.keySet()){
            System.out.println("name:" +crowdFunding.getId()+ " num:"+result.get(crowdFunding));
            crowdFundings.add(crowdFunding);
        }

        return crowdFundings;
    }

    @Override
    public List<Customer> findParticipants(long crowdFundingId) {
        CrowdFunding crowdFunding = crowdFundingDao.findByIdAndAlive(crowdFundingId,1);
        if (crowdFunding == null){
            return null;
        }
        List<CrowdFundOrder> crowdFundOrders = crowdFundOrderDao.findByCrowdFundingAndAlive(crowdFunding, 1);
        List<Customer> customers = new ArrayList<Customer>();
        for (CrowdFundOrder crowdFundOrder : crowdFundOrders){
            Customer c = crowdFundOrder.getCustomer();
            if (c != null || c.getAlive() == 1) {
                customers.add(c);
            }
        }
        return customers;
    }

    @Override
    public void checkIsFinish() {
        List<CrowdFunding> oldcrowdFundingList = new ArrayList<CrowdFunding>();
        List<CrowdFunding> list3 = crowdFundingDao.findByStateAndAlive(3,1);
        List<CrowdFunding> list4 = crowdFundingDao.findByStateAndAlive(4,1);
        oldcrowdFundingList.addAll(list3);
        oldcrowdFundingList.addAll(list4);
        List<CrowdFunding> newCrowdFundingList = new ArrayList<CrowdFunding>();
        for (CrowdFunding crowdFunding : oldcrowdFundingList){
            List<CrowdFundOrder> crowdFundOrders = crowdFundOrderDao.findByCrowdFundingAndAlive(crowdFunding,1);
            if (crowdFundOrders.size() != 0) {
                boolean flag = true;
                for (CrowdFundOrder crowdFundOrder : crowdFundOrders) {
                    if (crowdFundOrder.getState() != 2) {
                        flag = false;
                    }
                }
                if (flag) {
                    crowdFunding.setState(1);
                    newCrowdFundingList.add(crowdFunding);
                }
            }
        }
        if(newCrowdFundingList.size()!=0){
            crowdFundingDao.save(newCrowdFundingList);
        }
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
