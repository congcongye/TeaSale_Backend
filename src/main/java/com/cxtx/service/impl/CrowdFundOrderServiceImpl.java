package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.CreateCrowdFundOrderModel;
import com.cxtx.service.CrowdFundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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


    /**
     * 新增众筹订单
     * @param createCrowdFundOrderModel
     * @return
     */
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
        double needPay = 0;
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
        crowdFundOrder.setNum(createCrowdFundOrderModel.num);
        crowdFundOrder.setCrowdFunding(crowdFunding);
        crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
        if (crowdFunding.getType() == 0){//现付
            if (customer.getAccount().getMoney() >= createCrowdFundOrderModel.num * crowdFunding.getUnitMoney()
                    && crowdFunding.getRemainderNum() >= createCrowdFundOrderModel.num ){
                totalMoney += createCrowdFundOrderModel.num * crowdFunding.getUnitMoney();
                totalMoney += product.getIsFree()==1? 0:product.getPostage();
                needPay = totalMoney;
                crowdFundOrder.setRefund_state(1);
            }
        }
        if (crowdFunding.getType() == 1){//预付
            if (crowdFunding.getRemainderNum() >= createCrowdFundOrderModel.num
                    && customer.getAccount().getMoney() >= createCrowdFundOrderModel.num * crowdFunding.getEarnest()){
                totalMoney += createCrowdFundOrderModel.num * crowdFunding.getUnitMoney();
                totalMoney += product.getIsFree()==1? 0:product.getPostage();
                needPay = createCrowdFundOrderModel.num * crowdFunding.getEarnest();
                crowdFundOrder.setRefund_state(2);
            }
        }
        if (totalMoney > 0){
            account.setMoney(account.getMoney() - needPay);
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

    /**
     * 搜索众筹订单
     * @param customerId
     * @param teaSalerId
     * @param crowdFundingId
     * @param teaSalerName
     * @param state
     * @param isSend
     * @param isConfirm
     * @param customerDelete
     * @param adminDelete
     * @param salerDelete
     * @param refund_state
     * @param name
     * @param address
     * @param tel
     * @param beginDateStr
     * @param endDateStr
     * @param pageIndex
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @Override
    public Page<CrowdFundOrder> search(long customerId, long teaSalerId, long crowdFundingId, String teaSalerName, int state, int isSend, int isConfirm, int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address, String tel, String beginDateStr, String endDateStr, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }
        teaSalerName = "%" + teaSalerName + "%";
        Specification<CrowdFundOrder> specification = this.buildSpecification(customerId, teaSalerId,crowdFundingId,teaSalerName, state, isSend, isConfirm,  customerDelete, adminDelete,
                salerDelete, refund_state, name, address, tel, beginDateStr, endDateStr);
        Page<CrowdFundOrder> crowdFundOrders = crowdFundOrderDao.findAll(specification,new PageRequest(pageIndex, pageSize, direction,sortField));
        return crowdFundOrders;
    }

    private Specification<CrowdFundOrder> buildSpecification(final long customerId, //
                                                      final long teaSalerId, //
                                                      final long crowdFundingId,
                                                      final String teaSalerName,
                                                      final int state, //
                                                      final int isSend, //
                                                      final int isConfirm, //
                                                      final int customerDelete,
                                                      final int adminDelete,
                                                      final int salerDelete,
                                                      final int refund_state,
                                                      final String name, //
                                                      final String address, //
                                                      final String tel,
                                                      final String beginDateStr,
                                                      final String endDateStr) {//
        final  Customer customer = customerDao.findOne(customerId);
        final TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        final List<TeaSaler> teaSalers = teaSalerDao.findByNameAndAlive(teaSalerName, 1);
        final CrowdFunding crowdFunding = crowdFundingDao.findByIdAndAlive(crowdFundingId, 1);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Specification<CrowdFundOrder> specification = new Specification<CrowdFundOrder>() {
            @Override
            public Predicate toPredicate(Root<CrowdFundOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("name"),"%"+name+"%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("tel"),"%" + tel + "%"));
                predicate.getExpressions().add(criteriaBuilder.like(root.<String>get("address"),"%" + address + "%"));
                if (customer !=null && customer.getAlive() ==1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<Customer>get("customer"),customer));
                }
                if (teaSaler !=null && teaSaler.getAlive() ==1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<TeaSaler>get("teaSaler"),teaSaler));
                }else if(teaSalers != null && teaSalers.size() >0){
                    //predicate.getExpressions().add(root.<FileType>get("fileType").in(fileTypeList1));
                    predicate.getExpressions().add(root.<TeaSaler>get("teaSaler").in(teaSalers));
                }
                if (crowdFunding != null){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<CrowdFunding>get("crowdFunding"),crowdFunding));
                }
                if (state != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"),state));
                }
                if (isSend != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("isSend"),isSend));
                }
                if (isConfirm != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("isConfirm"),isConfirm));
                }
                if (customerDelete != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("customerDelete"),customerDelete));
                }
                if (adminDelete != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("adminDelete"),adminDelete));
                }
                if (salerDelete != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("salerDelete"),salerDelete));
                }
                if (refund_state != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("Refund_state"),refund_state));
                }
                if (beginDateStr != null && !"".equals(beginDateStr)){
                    Date beginDate = null;
                    try {
                        beginDate = sdf.parse(beginDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createDate"),beginDate));
                }
                if (endDateStr != null && !"".equals(endDateStr)){
                    Date endDate = null;
                    try {
                        endDate = sdf.parse(beginDateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("createDate"),endDate));
                }
                return predicate;
            }
        };
        return  specification;

    }
}
