package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
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
 * Created by ycc on 16/12/22.
 */
@Service("CrowdSourcingImpl")
public class CrowdSourcingImpl {

    @Autowired
    private CrowdSourcingDao crowdSourcingDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 众包的新增和修改
     * @param cs
     * @return
     */
    public CrowdSourcing newCrowdSourcing(CrowdSourcing cs){
        CrowdSourcing result=null;
        if(isUnique(cs)){
            Product p = cs.getProduct();
            p.setState(1);
            p.setCreateDate(new Date());//填入上架时间
            p.setType(2);//众包
            Product newProduct = productDao.save(p);
            cs.setProduct(newProduct);
            cs.setCreateDate(new Date());
            cs.setRemainderNum(cs.getTotalNum());
            result=crowdSourcingDao.save(cs);
        }
        return result;
    }
    private boolean isUnique(CrowdSourcing cs){
        List<CrowdSourcing> list= crowdSourcingDao.findByProductAndAlive(cs.getProduct(),1);
        if(null==list || list.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 确定众包订单是否已经删除
     * @param crowdSourcing
     * @return
     */
    public boolean isWorking(CrowdSourcing crowdSourcing){
        boolean flag=false;
        List<OrderItem> list =orderItemDao.findByProductAndAlive(crowdSourcing.getProduct(),1);
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
    /**
     * customer,产品名字，茶叶种类，众包状态分页
     * @return
     */
    public Page<CrowdSourcing> searchCrowdSourcing(Long customer_id, String productName, Long productType_id, int state, int pageIndex, int pageSize, String sortField, String sortOrder){
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortOrder.toUpperCase().equals("DESC")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortField);
        Specification<CrowdSourcing> specification = this.buildSpecifications(customer_id, productName, productType_id, state);
        return  crowdSourcingDao.findAll(Specifications.where(specification), new PageRequest(pageIndex, pageSize, sort));

    }

    private Specification<CrowdSourcing> buildSpecifications(Long customer_id,String productName,Long productType_id,int state) {

        Customer customer =customerDao.findByIdAndAlive(customer_id,1);
        ProductType productType=productTypeDao.findByIdAndAlive(productType_id,1);
        final int fstate=state;
        Specification<CrowdSourcing> specification = new Specification<CrowdSourcing>() {
            @Override
            public Predicate toPredicate(Root<CrowdSourcing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(null!=customer){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Customer>get("customer"),customer));
                }
                if(null!=productType){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Product>get("product").get("productType"),productType));
                }
                if(fstate>-1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"),fstate));
                }
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));
                predicate.getExpressions().add(criteriaBuilder.like(root.<Product>get("product").get("name"),"%"+productName+"%"));
                return criteriaBuilder.and(predicate);
            }
        };
        return specification;
    }


}
