package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.CreateCrowdFundOrderModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateOrderModel;
import com.cxtx.service.CrowdFundOrderService;
import com.cxtx.service.ManagerService;
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
    @Autowired
    private ManagerService managerService;


    /**
     * 新增众筹订单
     * @param createCrowdFundOrderModel
     * @return
     */
    @Override
    public ServiceResult insertOrder(CreateCrowdFundOrderModel createCrowdFundOrderModel) {

        Long customerId = createCrowdFundOrderModel.customerId;
        Long teaSalerId = createCrowdFundOrderModel.teaSalerId;
        Long crowdFundingId = createCrowdFundOrderModel.crowdFundingId;
        Customer customer = customerDao.findOne(customerId);
        TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        CrowdFunding crowdFunding = crowdFundingDao.findOne(crowdFundingId);
        if (customer == null || customer.getAlive() == 0 || teaSaler == null || teaSaler.getAlive() == 0 || crowdFunding == null || crowdFunding.getAlive() == 0){
            return ServiceResult.fail(500,"no customer, teasaler or crowd funding");
        }
        if (createCrowdFundOrderModel.num > crowdFunding.getRemainderNum()){
            //TODO
            return ServiceResult.fail(500,"众筹份额不够");
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
            totalMoney += createCrowdFundOrderModel.num * crowdFunding.getUnitMoney();
            totalMoney += product.getIsFree()==1? 0:product.getPostage();
            needPay = totalMoney;
        }
        if (crowdFunding.getType() == 1){//预付
            totalMoney += createCrowdFundOrderModel.num * crowdFunding.getUnitMoney();
            totalMoney += product.getIsFree()==1? 0:product.getPostage();
            needPay = createCrowdFundOrderModel.num * crowdFunding.getEarnest();
        }
        if (totalMoney > 0){
            if (account.getMoney() - needPay < 0){
                crowdFundOrder.setState(0);
                crowdFundOrder.setRefund_state(needPay==totalMoney?1:2);
                crowdFundOrder.setHasPay(needPay);
                crowdFundOrder.setTotalPrice(totalMoney);
                crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
            }else {
                account.setMoney(account.getMoney() - needPay);
                accountDao.save(account);
                Manager manager = managerService.getManager();
                managerService.changeMoney(manager,needPay,0);
                crowdFunding.setRemainderNum(crowdFunding.getRemainderNum() - createCrowdFundOrderModel.num);
                crowdFunding.setJoinNum(crowdFunding.getJoinNum() + 1);
                if (crowdFunding.getRemainderNum() <= 0){
                    if (crowdFunding.getType()==1){
                        crowdFunding.setState(3);
                    }else {
                        crowdFunding.setState(4);
                    }
                }
                crowdFundingDao.save(crowdFunding);
                crowdFundOrder.setState(1);
                crowdFundOrder.setRefund_state(needPay == totalMoney ? 1 : 2);
                crowdFundOrder.setHasPay(needPay);
                crowdFundOrder.setTotalPrice(totalMoney);
                crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
            }

        }

        return ServiceResult.success(crowdFundOrder);
    }

    /**Z
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

    @Override
    public ServiceResult payRemain(Long id) {
        CrowdFundOrder crowdFundOrder = crowdFundOrderDao.findOne(id);
        if (crowdFundOrder == null || crowdFundOrder.getAlive() == 0){
            return ServiceResult.fail(500, "no crowdfund order record");
        }
        if (crowdFundOrder.getRefund_state() != 2){
            return ServiceResult.fail(500, "the crowdfund order is not part pay order");
        }
        Account account = crowdFundOrder.getCustomer().getAccount();
        CrowdFunding crowdFunding = crowdFundOrder.getCrowdFunding();
        Product product = crowdFunding.getProduct();
        if (account == null || account.getAlive() == 0){
            return ServiceResult.fail(500, "no account record");
        }
        double needPay = crowdFundOrder.getTotalPrice() - crowdFundOrder.getHasPay();
        if (account .getMoney() >= needPay){
            account.setMoney(account.getMoney()-needPay);
            Manager manager = managerService.getManager();
            managerService.changeMoney(manager,needPay,0);
            accountDao.save(account);
            crowdFundOrder.setRefund_state(1);
            crowdFundOrder.setState(1);
            crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
            return ServiceResult.success(crowdFundOrder);
        }
        return ServiceResult.fail(500, "Sorry, your credit is running low");
    }

    /**
     * 确认收货
     * @param updateOrderModel
     * @return
     */
    @Override
    public CrowdFundOrder confirmOrder(UpdateOrderModel updateOrderModel) {
        CrowdFundOrder crowdFundOrder = crowdFundOrderDao.findOne(updateOrderModel.orderId);
        if (crowdFundOrder != null && crowdFundOrder.getAlive() == 1){
            Account account = crowdFundOrder.getTeaSaler().getAccount();
            account.setMoney(account.getMoney() + crowdFundOrder.getTotalPrice());
            accountDao.save(account);
            Manager manager = managerService.getManager();
            managerService.changeMoney(manager,crowdFundOrder.getTotalPrice(),1);
            crowdFundOrder.setConfirmDate(new Date());
            crowdFundOrder.setIsConfirm(1);
            crowdFundOrder.setState(2);
            return  crowdFundOrderDao.save(crowdFundOrder);
        }
        return null;
    }

    /**
     * 确认发货
     * @param updateOrderModel
     * @return
     */
    @Override
    public CrowdFundOrder sendOrder(UpdateOrderModel updateOrderModel) {
        CrowdFundOrder crowdFundOrder = crowdFundOrderDao.findOne(updateOrderModel.orderId);
        if (crowdFundOrder != null && crowdFundOrder.getAlive() == 1){
            crowdFundOrder.setIsSend(1);
            crowdFundOrder.setSendDate(new Date());
            crowdFundOrder.setWuliu(updateOrderModel.wuliu);
            return  crowdFundOrderDao.save(crowdFundOrder);
        }
        return null;
    }

    /**
     * 取消众筹订单
     * @param id
     * @return
     */
    @Override
    public ServiceResult cancelOrder(Long id) {
        CrowdFundOrder crowdFundOrder = crowdFundOrderDao.findOne(id);
        if (crowdFundOrder == null || crowdFundOrder.getAlive() == 0){
            return  ServiceResult.fail(500, "no crowdfund order record");
        }
        if (crowdFundOrder.getIsSend() == 1){
            return ServiceResult.fail(500,"crowdfund order has been send , can be canceled!");
        }
        crowdFundOrder = cancelCrowdFundOrder(crowdFundOrder);
        return ServiceResult.success(crowdFundOrder);
    }

    /**
     * 未完成继续支付
     * @param id
     * @return
     */
    @Override
    public ServiceResult payUnFinished(Long id) {
        CrowdFundOrder crowdFundOrder = crowdFundOrderDao.findOne(id);
        if (crowdFundOrder.getState() !=0){
            return ServiceResult.fail(500,"该订单已完成或者已取消,无法继续支付");
        }
        Account account = crowdFundOrder.getCustomer().getAccount();
        CrowdFunding crowdFunding = crowdFundOrder.getCrowdFunding();
        double needPay = crowdFundOrder.getHasPay();
        if (account.getMoney() - needPay < 0){
            return ServiceResult.fail(500,"账户金额不够");
        }else {
            account.setMoney(account.getMoney() - needPay);
            accountDao.save(account);
            Manager manager = managerService.getManager();
            managerService.changeMoney(manager,needPay,0);
            crowdFunding.setRemainderNum(crowdFunding.getRemainderNum() - crowdFundOrder.getNum());
            crowdFunding.setJoinNum(crowdFunding.getJoinNum() + 1);
            crowdFundingDao.save(crowdFunding);
            crowdFundOrder.setState(1);
            crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
        }
        return ServiceResult.success(crowdFundOrder);
    }

    private CrowdFundOrder cancelCrowdFundOrder(CrowdFundOrder crowdFundOrder) {
        Account account = crowdFundOrder.getCustomer().getAccount();
        account.setMoney(account.getMoney() + crowdFundOrder.getHasPay());
        accountDao.save(account);
        Manager manager = managerService.getManager();
        managerService.changeMoney(manager,crowdFundOrder.getHasPay(),1);
        crowdFundOrder.setState(3);
        crowdFundOrder = crowdFundOrderDao.save(crowdFundOrder);
        CrowdFunding crowdFunding = crowdFundOrder.getCrowdFunding();
        crowdFunding.setJoinNum(crowdFunding.getJoinNum()-1);
        crowdFunding.setRemainderNum(crowdFunding.getRemainderNum() + crowdFundOrder.getNum());
        crowdFundingDao.save(crowdFunding);
        return crowdFundOrder;
    }


    /**
     * 取消一个众筹包括的所有订单
     * @param crowdFunding
     */
    public void cancelOrdersByCrowdFund(CrowdFunding crowdFunding) {
        List<CrowdFundOrder> lists = crowdFundOrderDao.findByCrowdFundingAndAlive(crowdFunding,1);
        for (CrowdFundOrder crowdFundOrder : lists){
            cancelCrowdFundOrder(crowdFundOrder);
        }
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
        //final List<TeaSaler> teaSalers = teaSalerDao.findByNameAndAlive(teaSalerName, 1);
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
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));

                }
                predicate.getExpressions().add(criteriaBuilder.like(root.<TeaSaler>get("teaSaler").get("name"),teaSalerName));
                return predicate;
            }
        };
        return  specification;

    }


}
