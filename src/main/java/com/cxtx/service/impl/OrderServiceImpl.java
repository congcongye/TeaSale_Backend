package com.cxtx.service.impl;

import com.cxtx.dao.CustomerDao;
import com.cxtx.dao.OrderEnDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Customer;
import com.cxtx.entity.OrderEn;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.CreateOrderModel;
import com.cxtx.service.OrderItemService;
import com.cxtx.service.OrderService;
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
import java.util.Date;

/**
 * Created by ycc on 16/11/12.
 */
@Service("OrderServiceImpl")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private TeaSalerDao teaSalerDao;
    @Autowired
    private OrderEnDao orderEnDao;

    @Override
    public OrderEn insertOrder(CreateOrderModel createOrderModel) {
        long customerId = createOrderModel.customerId;
        long teaSalerId = createOrderModel.teaSalerId;
        Customer customer = customerDao.findOne(customerId);
        TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        if (customer == null || customer.getAlive() == 0 || teaSaler == null || teaSaler.getAlive() == 0){
            return null;
        }
        OrderEn orderEn = new OrderEn();
        orderEn.setTel(createOrderModel.tel);
        orderEn.setZip(createOrderModel.zip);
        orderEn.setAddress(createOrderModel.address);
        orderEn.setCustomer(customer);
        orderEn.setTeaSaler(teaSaler);
        orderEn.setType(createOrderModel.type);
        orderEn.setCreateDate(new Date());
        orderEn.setAlive(1);
        //orderEn.set
        return orderEnDao.save(orderEn);
    }

    @Override
    public Page<OrderEn> search(long customerId, long teaSalerId, int state, int isSend, int isConfirm, int isComment, int type, int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address, String tel, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }
        Specification<OrderEn> specification = this.buildSpecification(customerId, teaSalerId, state, isSend, isConfirm, isComment,type, customerDelete, adminDelete,
                salerDelete, refund_state, name, address, tel);
        Page<OrderEn> orders = orderEnDao.findAll(specification, new PageRequest(pageIndex, pageSize, direction,sortField));
        return orders;
    }

    private Specification<OrderEn> buildSpecification(final long customerId, //
                                                      final long teaSalerId, //
                                                      final int state, //
                                                      final int isSend, //
                                                      final int isConfirm, //
                                                      final int isComment, //
                                                      final int type,
                                                      final int customerDelete,
                                                      final int adminDelete,
                                                      final int salerDelete,
                                                      final int refund_state,
                                                      final String name, //
                                                      final String address, //
                                                      final String tel) {//
        final  Customer customer = customerDao.findOne(customerId);
        final TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        Specification<OrderEn> specification = new Specification<OrderEn>() {
            @Override
            public Predicate toPredicate(Root<OrderEn> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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
                if (state != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("state"),state));
                }
                if (isSend != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("isSend"),isSend));
                }
                if (isConfirm != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("isConfirm"),isConfirm));
                }
                if (isComment != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("isComment"),isComment));
                }
                if (type != -1){
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"),type));
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
                return predicate;
            }
        };
        return  specification;
    }
}
