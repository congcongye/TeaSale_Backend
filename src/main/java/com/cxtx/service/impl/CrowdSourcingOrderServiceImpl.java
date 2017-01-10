package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateOrderModel;
import com.cxtx.model.newCrowdSourcingOrderModel;
import com.cxtx.service.CrowdSourcingOrderService;
import com.cxtx.service.CrowdSourcingService;
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
 * Created by ycc on 16/12/24.
 */
@Service("CROWDSOURCINGORDERIMPL")
public class CrowdSourcingOrderServiceImpl implements CrowdSourcingOrderService{

    @Autowired
    private CrowdSourcingDao crowdSourcingDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TeaSalerDao teaSalerDao;
    @Autowired
    private CrowdSourcingOrderDao crowdSourcingOrderDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ManagerService managerService;


    /**
     * 参与众包,不改变茶农或消费者用户的钱
     * @param csorder
     * @return
     */
    public ServiceResult insert(newCrowdSourcingOrderModel csorder){
        Long customerId = csorder.customerId;
        Long teaSalerId = csorder.teaSalerId;
        Long crowdSourcingId = csorder.crowdSourcingId;
        Customer customer = customerDao.findOne(customerId);
        TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        CrowdSourcing crowdSourcing = crowdSourcingDao.findOne(crowdSourcingId);
        if (customer == null || customer.getAlive() == 0 || teaSaler == null || teaSaler.getAlive() == 0 || crowdSourcing == null || crowdSourcing.getAlive() == 0){
            return ServiceResult.fail(500,"no customer, teasaler or crowd funding");
        }
        if (csorder.num < crowdSourcing.getUnitNum()){
            //TODO
            return ServiceResult.fail(500,"购买数量少于剩余量");
        }
        CrowdSourcingOrder crowdSourcingOrder = new CrowdSourcingOrder();
        if(crowdSourcing.getState()==0&&crowdSourcing.getRemainderNum()>=csorder.num){
            double totalMoney = 0;
            totalMoney = csorder.num * crowdSourcing.getUnitMoney();
            crowdSourcingOrder.setTel(csorder.tel);
            crowdSourcingOrder.setZip(csorder.zip);
            crowdSourcingOrder.setAddress(csorder.address);
            crowdSourcingOrder.setCustomer(customer);
            crowdSourcingOrder.setTeaSaler(teaSaler);
            crowdSourcingOrder.setName(csorder.name);
            crowdSourcingOrder.setCreateDate(new Date());
            crowdSourcingOrder.setAlive(1);
            crowdSourcingOrder.setState(0);
            crowdSourcingOrder.setNum(csorder.num);
            crowdSourcingOrder.setCrowdSourcing(crowdSourcing);
//            crowdSourcingOrder = crowdSourcingOrderDao.save(crowdSourcingOrder);
//            crowdSourcingOrder.setRefund_state(1);改为0
            crowdSourcing.setRemainderNum(crowdSourcing.getRemainderNum() - csorder.num);
            crowdSourcing.setJoinNum(crowdSourcing.getJoinNum() + 1);
            crowdSourcingDao.save(crowdSourcing);
//            crowdSourcingOrder.setState(2);
            crowdSourcingOrder.setTotalPrice(totalMoney);
            crowdSourcingOrder = crowdSourcingOrderDao.save(crowdSourcingOrder);
        }else{
            return ServiceResult.fail(500,"众包已经结束或者购买数量超过剩余量");
        }
        if(crowdSourcing.getRemainderNum()==0){
            crowdSourcing.setState(3);
            crowdSourcingDao.save(crowdSourcing);
        }
        return ServiceResult.success(crowdSourcingOrder);
    }


    /**
     * 给茶农加上众包订单的钱,确认收货
     * @param updateOrderModel
     * @return
     */
    public CrowdSourcingOrder confirmOrder(UpdateOrderModel updateOrderModel) {
        CrowdSourcingOrder order = crowdSourcingOrderDao.findOne(updateOrderModel.orderId);
        if (order != null && order.getAlive() == 1){
            Account account = order.getTeaSaler().getAccount();
            account.setMoney(account.getMoney()+order.getTotalPrice());
            accountDao.save(account);
            order.setConfirmDate(new Date());
            order.setIsConfirm(1);
            order.setState(2);
            Manager manager=managerService.getManager();
            managerService.changeMoney(manager,order.getTotalPrice(),1);
            return  crowdSourcingOrderDao.save(order);
        }
        return null;
    }

    /**
     * 发货
     * @param updateOrderModel
     * @return
     */
    public CrowdSourcingOrder sendOrder(UpdateOrderModel updateOrderModel) {
        CrowdSourcingOrder order = crowdSourcingOrderDao.findByIdAndAlive(updateOrderModel.orderId,1);
        if (order != null && order.getState() == 0){
            order.setIsSend(1);
            order.setSendDate(new Date());
            order.setState(1);//改成已发货
            order.setWuliu(updateOrderModel.wuliu);
            return  crowdSourcingOrderDao.save(order);
        }
        return null;
    }

    /**
     * 取消订单,没发货之前可以取消
     * @param id
     * @return
     */
    public ServiceResult cancelOrder(Long id) {
        CrowdSourcingOrder order = crowdSourcingOrderDao.findOne(id);
        if (order == null || order.getAlive() == 0){
            return  ServiceResult.fail(500, "no crowdfund order record");
        }
        if (order.getCrowdSourcing().getState() == 1){ //众包成功之后,不能取消
            return ServiceResult.fail(500,"crodfunding has sended , can be canceled!");
        }
        order.setState(3);
        CrowdSourcing cs =order.getCrowdSourcing();
        cs.setRemainderNum(cs.getRemainderNum()+order.getNum());
        cs.setJoinNum(cs.getJoinNum() - 1);
        crowdSourcingDao.save(cs);
        order = crowdSourcingOrderDao.save(order);
        return ServiceResult.success(order);
    }

    /**
     * 搜索众包订单
     * @param customerId
     * @param teaSalerId
     * @param crowdSourcingId
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
    public Page<CrowdSourcingOrder> search(long customerId, long teaSalerId, long crowdSourcingId, String teaSalerName, int state, int isSend, int isConfirm, int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address, String tel, String beginDateStr, String endDateStr, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }
        teaSalerName = "%" + teaSalerName + "%";
        Specification<CrowdSourcingOrder> specification = this.buildSpecification(customerId, teaSalerId,crowdSourcingId,teaSalerName, state, isSend, isConfirm,  customerDelete, adminDelete,
                salerDelete, refund_state, name, address, tel, beginDateStr, endDateStr);
        Page<CrowdSourcingOrder> crowdSourcingOrders = crowdSourcingOrderDao.findAll(specification,new PageRequest(pageIndex, pageSize, direction,sortField));
        return crowdSourcingOrders;
    }

    private Specification<CrowdSourcingOrder> buildSpecification(final long customerId, //
                                                             final long teaSalerId, //
                                                             final long crowdSourcingId,
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
        final CrowdSourcing crowdSourcing = crowdSourcingDao.findByIdAndAlive(crowdSourcingId, 1);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Specification<CrowdSourcingOrder> specification = new Specification<CrowdSourcingOrder>() {
            @Override
            public Predicate toPredicate(Root<CrowdSourcingOrder> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
                if (crowdSourcing != null){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.<CrowdSourcing>get("crowdSourcing"),crowdSourcing));
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
                predicate.getExpressions().add(criteriaBuilder.like(root.<TeaSaler>get("teaSaler").get("name"),teaSalerName));
                return predicate;
            }
        };
        return  specification;
    }
}
