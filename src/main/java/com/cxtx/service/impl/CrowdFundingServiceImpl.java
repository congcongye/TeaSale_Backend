package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.IdModel;
import com.cxtx.model.UpdateCrowdFundingModel;
import com.cxtx.service.CrowdFundingService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/11/26.
 */
@Service("CrowdFundingServiceImpl")
public class CrowdFundingServiceImpl implements CrowdFundingService {

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


    private boolean isWorking(CrowdFunding crowdFunding){
        boolean flag=false;
        List<OrderItem> list =orderItemDao.findByProductAndAlive(crowdFunding.getProduct(),1);
        if(list!=null){
           for(OrderItem orderItem:list) {
               if(orderItem!=null&&orderItem.getOrderen()!=null){
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
        List<CrowdFunding> oldcrowdFundingList = crowdFundingDao.findByAlive(1);
        List<CrowdFunding> newCrowdFundingList = new ArrayList<CrowdFunding>();
        for (CrowdFunding crowdFunding : oldcrowdFundingList){
            Date dealDate = crowdFunding.getDealDate();
            Date now = new Date();
            if (now.after(dealDate)){
                if (crowdFunding.getRemainderNum() <= 0){
                    crowdFunding.setState(1);
                }else{
                    crowdFunding.setState(2);
                }
            }
            newCrowdFundingList.add(crowdFunding);
        }
        crowdFundingDao.save(newCrowdFundingList);
    }

    @Override
    public CrowdFunding confirmCrowdFunding(Long id) {
        CrowdFunding crowdFunding = crowdFundingDao.findByIdAndAlive(id, 1);
        if (crowdFunding == null || crowdFunding.getAlive() == 0) {
            return null;
        }
        crowdFunding.setState(1);//成功
        return  crowdFundingDao.save(crowdFunding);
    }

}