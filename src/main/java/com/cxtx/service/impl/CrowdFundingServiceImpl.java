package com.cxtx.service.impl;

import com.cxtx.dao.CrowdFundingDao;
import com.cxtx.dao.OrderItemDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.OrderItem;
import com.cxtx.entity.Product;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.IdModel;
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
    public CrowdFunding updateCrowdFunding(CrowdFunding model){
         CrowdFunding result=null;
        if(isWorking(model)==false){
            result=crowdFundingDao.save(model);
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
    public List<CrowdFunding> searchCrowdFunding(Long product_id, Long teaSaler_id, int type, double lowEarnest, double highEarnest, double lowUnitNum, double highUnitNum, double lowUnitMoney, double highUnitMoney, int state, double lowRemainderNum, double highRemainderNum){
        Specification<CrowdFunding> specification = this.buildSpecifications(product_id, teaSaler_id, type, lowEarnest, highEarnest, lowUnitNum, highUnitNum, lowUnitMoney, highUnitMoney, state, lowRemainderNum, highRemainderNum);
        return  crowdFundingDao.findAll(Specifications.where(specification));

    }

    private Specification<CrowdFunding> buildSpecifications(Long product_id,Long teaSaler_id,int type,double lowEarnest,double highEarnest,double lowUnitNum,double highUnitNum,double lowUnitMoney,double highUnitMoney,int state,double lowRemainderNum,double highRemainderNum) {
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
}
