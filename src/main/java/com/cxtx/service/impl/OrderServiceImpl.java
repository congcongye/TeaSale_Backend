package com.cxtx.service.impl;

import com.cxtx.dao.*;
import com.cxtx.entity.*;
import com.cxtx.model.CreateOrderItemModel;
import com.cxtx.model.CreateOrderModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateOrderModel;
import com.cxtx.service.ManagerService;
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
import java.util.*;

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
    @Autowired
    private ManagerService managerService;

    /**
     * 新增订单,根据orderModel返回订单列表
     * @param createOrderModels
     * @return
     */
    @Override
    public Map<String,Object> insertOrders(List<CreateOrderModel> createOrderModels) {
        List<OrderEn> orderEns = new ArrayList<OrderEn>();
        Map<String,Object> result =new HashMap<String,Object>();
        int index=1;
        String msg="";
        for ( CreateOrderModel createOrderModel : createOrderModels){
            long customerId = createOrderModel.customerId;
            long teaSalerId = createOrderModel.teaSalerId;
            Customer customer = customerDao.findOne(customerId);
            TeaSaler teaSaler = teaSalerDao.findOne(teaSalerId);
            if (customer == null || customer.getAlive() == 0){//无效判断
                msg=msg+"第"+index+"个订单的消费者不存在;\n";
                continue;
            }
            if( teaSaler == null || teaSaler.getAlive() == 0||teaSaler.getState()!=1){
                msg=msg+"第"+index+"个订单的茶农不存在或审核未通过;\n";
                continue;
            }
            OrderEn orderEn = new OrderEn();
            orderEn.setTel(createOrderModel.tel);
            orderEn.setZip(createOrderModel.zip);
            orderEn.setAddress(createOrderModel.address);
            orderEn.setCustomer(customer);
            orderEn.setTeaSaler(teaSaler);
            orderEn.setName(createOrderModel.name);
            orderEn.setCreateDate(new Date());
            orderEn.setAlive(1);
            orderEn.setState(0);//此时订单的状态是刚创建,并没有完成,所以状态取1
            orderEn = orderEnDao.save(orderEn);//新建一个订单,填入必须项

            List<CreateOrderItemModel> createOrderItemModels = createOrderModel.createOrderItemModels;
            List<OrderItem> orderItems = new ArrayList<OrderItem>();//用于存储这个订单内的订单项,因为存在整个订单价格超过用户账户的钱,所以不能先存入数据库
            List<Product> products =  new ArrayList<Product>();//用于存储此订单内包含的产品,因为一个订单内产品是会合并的,所以不用担心重复
            double totalMoney = 0;//此订单总价
            double logistic = -1;//此订单包含的最高的运费
            int productIndex=1;
            for (CreateOrderItemModel createOrderItemModel : createOrderItemModels){
                long productId = createOrderItemModel.productId;
                // long orderEnId = createOrderItemModel.orderEnId;
                Product product = productDao.findOne(productId);
                if (product == null || product.getAlive() == 0){
                    msg=msg+"第"+index+"个订单的第"+productIndex+"个商品不存在;\n";
                    break;
                }
                OrderItem orderItem = new OrderItem();
                orderItem.setAlive(1);
                if (product.getStock() < createOrderItemModel.num){
                    msg=msg+"第"+index+"个订单的第"+productIndex+"个商品的库存不足;\n";
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
                productIndex++;
            }
            if (orderItems.size()==createOrderItemModels.size() && customer.getAccount().getMoney() >((totalMoney + logistic))){
                orderItemDao.save(orderItems);
                products = productDao.save(products);
                orderEn.setTotalPrice(totalMoney + logistic);
                orderEn.setState(1);
                orderEn = orderEnDao.save(orderEn);
                customer.getAccount().setMoney(customer.getAccount().getMoney() -((totalMoney + logistic)));
                //TODO manager account add money
                Manager manager = managerService.getManager();
                managerService.changeMoney(manager,orderEn.getTotalPrice(),1);
                //删除购物车
                for (Product product : products){
                    Cart cart = cartDao.findByProductAndCustomerAndAlive(product, customer, 1);
                    if (cart != null && cart.getAlive() == 1){
                        cart.setAlive(0);
                        cartDao.save(cart);
                    }
                }
                orderEns.add(orderEn);
            }
//            else{
//                orderItemDao.save(orderItems);
//                orderEn.setTotalPrice(totalMoney + logistic);
//                orderEn.setState(0);
//                orderEn = orderEnDao.save(orderEn);
//            }
            index++;
        }
        if(createOrderModels.size()==orderEns.size()){
            result.put("num",orderEns.size());
            result.put("msg","全部创建成功");
            result.put("content",orderEns);
        }else{
            result.put("num",orderEns.size());
            result.put("msg",msg);
            result.put("content",orderEns);
        }
        return result;
    }

    /**
     * 确定订单
     * @param updateOrderModel
     * @return
     */
    @Override
    public OrderEn confirmOrder(UpdateOrderModel updateOrderModel) {
        OrderEn orderEn = orderEnDao.findOne(updateOrderModel.orderId);
        if (orderEn != null && orderEn.getAlive() == 1 && orderEn.getIsSend() == 1){
            Account account = orderEn.getTeaSaler().getAccount();
            account.setMoney(account.getMoney() + orderEn.getTotalPrice());
            accountDao.save(account);
            //TODO manager account reduce money
            Manager manager = managerService.getManager();
            managerService.changeMoney(manager,orderEn.getTotalPrice(),1);
            orderEn.setIsConfirm(1);
            orderEn.setConfirmDate(new Date());
            orderEn.setState(2);
            return  orderEnDao.save(orderEn);
        }
        return null;
    }

    @Override
    public OrderEn sendOrder(UpdateOrderModel updateOrderModel) {
        OrderEn orderEn = orderEnDao.findOne(updateOrderModel.orderId);
        if (orderEn != null && orderEn.getAlive() == 1){
            orderEn.setIsSend(1);
            orderEn.setSendDate(new Date());
            orderEn.setWuliu(updateOrderModel.wuliu);
            return  orderEnDao.save(orderEn);
        }
        return null;
    }

    @Override
    public ServiceResult cancelOrder(Long id) {
        OrderEn orderEn = orderEnDao.findOne(id);
        if (orderEn == null || orderEn.getAlive() == 0 ){
            return ServiceResult.fail(500,"no order record");
        }
        if (orderEn.getIsSend() == 1){
            return ServiceResult.fail(500,"product has sended , can be canceled!");
        }
        Customer customer = orderEn.getCustomer();
        Account account = customer.getAccount();
        account.setMoney(account.getMoney() + orderEn.getTotalPrice());
        accountDao.save(account);
        Manager manager = managerService.getManager();
        managerService.changeMoney(manager,orderEn.getTotalPrice(),1);
        orderEn.setState(3);
        orderEn = orderEnDao.save(orderEn);
        return ServiceResult.success(orderEn);
    }

    @Override
    public ServiceResult payUnFinished(Long id) {
        OrderEn orderEn = orderEnDao.findOne(id);
        Account account = orderEn.getCustomer().getAccount();
        if (orderEn == null || orderEn.getAlive()==0 ||account == null || account.getAlive() == 0){
            return  ServiceResult.fail(500, "存在空的帐号或者空的订单");
        }
        if (orderEn.getTotalPrice() > account.getMoney()){
            return ServiceResult.fail(500, "账户金额不足");
        }
        account.setMoney(account.getMoney() - orderEn.getTotalPrice());
        accountDao.save(account);
        Manager manager = managerService.getManager();
        managerService.changeMoney(manager,orderEn.getTotalPrice(),0);
        orderEn.setState(1);
        orderEn  = orderEnDao.save(orderEn);
        return ServiceResult.success(orderEn);
    }

    @Override
    public Page<OrderEn> search(long customerId, long teaSalerId, String teaSalerName, int state, int isSend, int isConfirm, int isComment,  int customerDelete, int adminDelete, int salerDelete,
                                int refund_state, String name, String address, String tel, String beginDateStr, String endDateStr, int pageIndex, int pageSize, String sortField, String sortOrder) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sortOrder.toUpperCase().equals("ASC")) {
            direction = Sort.Direction.ASC;
        }
        teaSalerName = "%" + teaSalerName + "%";


        Specification<OrderEn> specification = this.buildSpecification(customerId, teaSalerId,teaSalerName, state, isSend, isConfirm, isComment, customerDelete, adminDelete,
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
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("alive"),1));
                return predicate;
            }
        };
        return  specification;
    }
}
