package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.service.CrowdSourcingService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ycc on 16/12/22.
 */
@Service("CrowdSourcingImpl")
public class CrowdSourcingServiceImpl implements CrowdSourcingService{

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
    @Autowired
    private CrowdSourcingOrderDao crowdSourcingOrderDao;
    @Autowired
    private AccountDao accountDao;


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

    /**
     * 众包定时检查是否到dd,众包成功
     */
    @Override
    public void checkNum() {
        System.out.print("众包是否成功定时任务");
        List<CrowdSourcing> oldCrowdSourcingList = crowdSourcingDao.findByAlive(1);
        List<CrowdSourcing> newCrowdSourcingList = new ArrayList<CrowdSourcing>();
        for (CrowdSourcing crowdSourcing : oldCrowdSourcingList){
            Date dealDate = crowdSourcing.getDealDate();
            Date now = new Date();
            if (now.after(dealDate)&&crowdSourcing.getState()==0){
                if (crowdSourcing.getRemainderNum() <= 0){
                    crowdSourcing.setState(3);
                }else{
                    crowdSourcing.setState(2);
                }
            }
            newCrowdSourcingList.add(crowdSourcing);
        }
        crowdSourcingDao.save(newCrowdSourcingList);
    }

    @Override
    public void checkIsFinish() {
        System.out.print("是否全部发货定时任务");
        List<CrowdSourcing> oldcrowdSourcingList = crowdSourcingDao.findByAliveAndState(1,3);
        List<CrowdSourcing> newCrowdSourcingList = new ArrayList<CrowdSourcing>();
        for (CrowdSourcing crowdSourcing : oldcrowdSourcingList){
            List<CrowdSourcingOrder> crowdSourcingOrders = crowdSourcingOrderDao.findByCrowdSourcingAndAlive(crowdSourcing,1);
            boolean flag = true;
            for (CrowdSourcingOrder order : crowdSourcingOrders){
                if (order.getState() != 2){
                    flag = false;
                }
            }
            if (flag) {
                crowdSourcing.setState(1);
                crowdSourcingDao.save(crowdSourcing);
            }
        }

    }

    @Override
    public void addCustomerMoney(){
        System.out.println("执行开始");
        List<CrowdSourcing> crowdSourcings =crowdSourcingDao.findByAlive(1);
        for(CrowdSourcing c:crowdSourcings){
            Customer customer=c.getCustomer();
            Account account=customer.getAccount();
            if(c.getState()==2){ //未成功,退还消费者全部金额
                account.setMoney(account.getMoney()+c.getTotalNum()*c.getUnitMoney());
                accountDao.save(account);
                System.out.println("退钱: "+c.getId()+" 众包状态: "+c.getState());
                //扣除系统的钱
                c.setState(5);
                crowdSourcingDao.save(c);
            }
            List<CrowdSourcingOrder> orders=new ArrayList<CrowdSourcingOrder>();
            if(c.getState()==1){//成功,则计算金额,把多余的金额退还
                List<CrowdSourcingOrder> list =crowdSourcingOrderDao.findByCrowdSourcingAndAlive(c,1);
                double totalMoney=0;
                for(CrowdSourcingOrder order:list){
                    if(order.getState()==2){
                        totalMoney=totalMoney+order.getNum()*order.getCrowdSourcing().getUnitMoney();
                        order.setState(4);
                        orders.add(order);
                    }
                }
                System.out.println("退部分钱: "+c.getId()+" 众包状态: "+c.getState());
                double addMoney=c.getTotalNum()*c.getUnitMoney()-totalMoney;
                account.setMoney(addMoney);
                accountDao.save(account);
                crowdSourcingOrderDao.save(orders);
                c.setState(4);
                crowdSourcingDao.save(c);
            }

        }
    }

    @Override
    public List<TeaSaler> findParticipants(long crowdSourcingId) {
        CrowdSourcing crowdSourcing = crowdSourcingDao.findByIdAndAlive(crowdSourcingId,1);
        if (crowdSourcing == null){
            return null;
        }
        List<CrowdSourcingOrder> crowdSourcingOrders = crowdSourcingOrderDao.findByCrowdSourcingAndAlive(crowdSourcing,1);
        List<TeaSaler> teaSalers = new ArrayList<TeaSaler>();
        for (CrowdSourcingOrder crowdSourcingOrder : crowdSourcingOrders){
            TeaSaler t = crowdSourcingOrder.getTeaSaler();
            if (t != null || t.getAlive() == 1) {
                teaSalers.add(t);
            }
        }
        return teaSalers;
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
