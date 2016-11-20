package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.CreateOrderItemModel;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderItemDao orderItemDao;

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
    public List<OrderEn> insertOrders(List<CreateOrderModel> createOrderModels) {
        List<OrderEn> orderEns = new ArrayList<OrderEn>();
        for ( CreateOrderModel createOrderModel : createOrderModels){
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
            orderEn.setName(createOrderModel.name);
            orderEn.setCreateDate(new Date());
            orderEn.setAlive(1);
            orderEn = orderEnDao.save(orderEn);
            List<OrderItem> orderItems = new ArrayList<OrderItem>();
            List<Product> products =  new ArrayList<Product>();
            double totalMoney = 0;
            double logistic = -1;
            List<CreateOrderItemModel> createOrderItemModels = createOrderModel.createOrderItemModels;
            for (CreateOrderItemModel createOrderItemModel : createOrderItemModels){
                long productId = createOrderItemModel.productId;
                // long orderEnId = createOrderItemModel.orderEnId;
                Product product = productDao.findOne(productId);
                if (product == null || product.getAlive() == 0){
                    break;
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setAlive(1);
                if (product.getStock() < createOrderItemModel.num){
                    break;
                }
                product.setStock(product.getStock()-createOrderItemModel.num);
                products.add(product);
                if (logistic == 0 || product.getIsFree()==1 ){
                    logistic=0;
                }else {
                    if (product.getPostage() > logistic){
                        logistic = product.getPostage();
                    }
                }
                orderItem.setNum(createOrderItemModel.num);
                orderItem.setProduct(product);
                orderItem.setOrderen(orderEn);
                orderItem.setTotalPrice(createOrderItemModel.num * product.getPrice() * product.getDiscount());
                totalMoney += createOrderItemModel.num * product.getPrice() * product.getDiscount();
                orderItems.add(orderItem);
            }
            if (orderItems.size()==createOrderItemModels.size() && customer.getAccount().getMoney() >((totalMoney + logistic))){
                orderItems = orderItemDao.save(orderItems);
                products = productDao.save(products);
                orderEn.setTotalPrice(totalMoney + logistic);
                orderEn.setState(1);
                orderEnDao.save(orderEn);
                customer.getAccount().setMoney(customer.getAccount().getMoney() -((totalMoney + logistic)));
                orderEns.add(orderEn);
            }

        }
        return orderEns;
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
