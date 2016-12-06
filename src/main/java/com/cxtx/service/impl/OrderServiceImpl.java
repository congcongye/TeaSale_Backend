package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.CreateOrderItemModel;
import com.cxtx.model.CreateOrderModel;
import com.cxtx.model.UpdateOrderModel;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private CartDao cartDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private CrowdFundingDao crowdFundingDao;

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
            orderEn.setState(0);
            orderEn = orderEnDao.save(orderEn);
            List<CreateOrderItemModel> createOrderItemModels = createOrderModel.createOrderItemModels;
            if (customer == null || customer.getAlive() == 0 || teaSaler == null || teaSaler.getAlive() == 0){
                continue;
            }
            if (createOrderModel.type == 1){
                CrowdFunding crowdFunding = crowdFundingDao.findByIdAndAlive(createOrderModel.crowdFundingId, 1);
                if (crowdFunding == null){
                    continue;
                }
                double totalMoney = 0;
                for (CreateOrderItemModel createOrderItemModel : createOrderItemModels){
                    long productId = createOrderItemModel.productId;
                    Product product = productDao.findOne(productId);
                    if (product == null || product.getAlive() == 0){
                        break;
                    }
                    if (crowdFunding.getType() == 0){
                        if (customer.getAccount().getMoney() > createOrderItemModel.num * product.getPrice() * product.getDiscount()
                                && crowdFunding.getRemainderNum() > createOrderItemModel.num ){
                            OrderItem orderItem = new OrderItem();
                            orderItem.setAlive(1);
                            orderItem.setTotalPrice(createOrderItemModel.num * product.getPrice() * product.getDiscount());
                            totalMoney += createOrderItemModel.num * product.getPrice() * product.getDiscount();
                            orderItem.setNum(createOrderItemModel.num);
                            orderItem.setIsComment(0);
                            orderItem.setProduct(product);
                            orderItem.setOrderen(orderEn);
                            orderItemDao.save(orderItem);
                            Account account = customer.getAccount();
                            account.setMoney(account.getMoney() - (createOrderItemModel.num * product.getPrice() * product.getDiscount()));
                            accountDao.save(account);
                            orderEn.setState(1);
                            orderEn.setTotalPrice(totalMoney);
                            orderEn = orderEnDao.save(orderEn);
                            orderEns.add(orderEn);
                            crowdFunding.setRemainderNum(crowdFunding.getRemainderNum() - createOrderItemModel.num);
                            crowdFunding.setJoinNum(crowdFunding.getJoinNum() + 1);
                            crowdFundingDao.save(crowdFunding);
                        }
                    } else {
                        if (crowdFunding.getRemainderNum() >= createOrderItemModel.num
                                && customer.getAccount().getMoney() > createOrderItemModel.num * crowdFunding.getEarnest()){

                            OrderItem orderItem = new OrderItem();
                            orderItem.setAlive(1);
                            orderItem.setTotalPrice(createOrderItemModel.num * product.getPrice() * product.getDiscount());
                            totalMoney += createOrderItemModel.num * product.getPrice() * product.getDiscount();
                            orderItem.setNum(createOrderItemModel.num);
                            orderItem.setIsComment(0);
                            orderItem.setOrderen(orderEn);
                            orderItemDao.save(orderItem);
                            Account account = customer.getAccount();
                            account.setMoney(account.getMoney() - (createOrderItemModel.num * crowdFunding.getEarnest()));
                            accountDao.save(account);
                            orderEn.setState(2);
                            orderEn.setTotalPrice(totalMoney);
                            orderEn = orderEnDao.save(orderEn);
                            orderEns.add(orderEn);
                            crowdFunding.setRemainderNum(crowdFunding.getRemainderNum() - createOrderItemModel.num);
                            crowdFunding.setJoinNum(crowdFunding.getJoinNum() + 1);
                            crowdFundingDao.save(crowdFunding);
                        }
                    }

                }
            }
            if (createOrderModel.type == 0){
                List<OrderItem> orderItems = new ArrayList<OrderItem>();
                List<Product> products =  new ArrayList<Product>();
                double totalMoney = 0;
                double logistic = -1;
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
                    orderEn = orderEnDao.save(orderEn);
                    customer.getAccount().setMoney(customer.getAccount().getMoney() -((totalMoney + logistic)));
                    //TODO manager account add money
                    for (Product product : products){
                        Cart cart = cartDao.findByProductAndCustomerAndAlive(product, customer, 1);
                        if (cart != null && cart.getAlive() == 1){
                            cart.setAlive(0);
                            cartDao.save(cart);
                        }

                    }
                }
            }
            orderEns.add(orderEn);

        }
        return orderEns;
    }

    @Override
    public OrderEn confirmOrder(UpdateOrderModel updateOrderModel) {
        OrderEn orderEn = orderEnDao.findOne(updateOrderModel.orderId);
        if (orderEn != null && orderEn.getAlive() == 1){
            Account account = orderEn.getTeaSaler().getAccount();
            account.setMoney(account.getMoney() + orderEn.getTotalPrice());
            accountDao.save(account);
            //TODO manager account reduce money
            orderEn.setIsConfirm(1);
            return  orderEnDao.save(orderEn);
        }
        return null;
    }

    @Override
    public OrderEn sendOrder(UpdateOrderModel updateOrderModel) {
        OrderEn orderEn = orderEnDao.findOne(updateOrderModel.orderId);
        if (orderEn != null && orderEn.getAlive() == 1){
            orderEn.setIsSend(1);
            return  orderEnDao.save(orderEn);
        }
        return null;
    }

    @Override
    public Page<OrderEn> search(long customerId, long teaSalerId, String teaSalerName, int state, int isSend, int isConfirm, int isComment, int type, int customerDelete, int adminDelete, int salerDelete, int refund_state, String name, String address, String tel, String beginDateStr, String endDateStr, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }
        teaSalerName = "%" + teaSalerName + "%";


        Specification<OrderEn> specification = this.buildSpecification(customerId, teaSalerId,teaSalerName, state, isSend, isConfirm, isComment,type, customerDelete, adminDelete,
                salerDelete, refund_state, name, address, tel, beginDateStr, endDateStr);
        Page<OrderEn> orders = orderEnDao.findAll(specification, new PageRequest(pageIndex, pageSize, direction,sortField));
        return orders;
    }

    private Specification<OrderEn> buildSpecification(final long customerId, //
                                                      final long teaSalerId, //
                                                      final String teaSalerName,
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
                                                      final String tel,
                                                      final String beginDateStr,
                                                      final String endDateStr) {//
        final  Customer customer = customerDao.findOne(customerId);
        final TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
        final List<TeaSaler> teaSalers = teaSalerDao.findByNameAndAlive(teaSalerName, 1);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                }else if(teaSalers != null && teaSalers.size() >0){
                    //predicate.getExpressions().add(root.<FileType>get("fileType").in(fileTypeList1));
                    predicate.getExpressions().add(root.<TeaSaler>get("teaSaler").in(teaSalers));
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
