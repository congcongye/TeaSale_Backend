package com.cxtx.service.impl;

import com.cxtx.dao.OrderEnDao;
import com.cxtx.dao.OrderItemDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.*;
import com.cxtx.model.StatisticsAllProductTypes;
import com.cxtx.model.StatisticsProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 17/1/3.
 */
@Service("StatisticsServiceImpl")
public class StatisticsServiceImpl {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private OrderEnDao orderEnDao;
    @Autowired
    private ProductDao productDao;
    /**
     * 统计茶农所有产品某个时间段内的销售量
     * @param teaSaler
     * @param start
     * @param end
     * @return
     */
    public Map<Long,Object> CountByTeaSalerAndProductAndDate(TeaSaler teaSaler, Date start, Date end){
        Map<Long,Object> result =new HashMap<Long,Object>();
        List<OrderEn> list = orderEnDao.findByTeaSalerAndAliveAndCreateDate(teaSaler.getId(),1,start,end);
        for(OrderEn order:list){
            List<OrderItem> orderItems =orderItemDao.findByOrderenAndAlive(order,1);
            for(OrderItem orderItem:orderItems){
                if(orderItem.getOrderen().getState()==2){ //订单成功才统计
                    Product product=orderItem.getProduct();
                    StatisticsProductModel model =null;
                    if(result.containsKey(product.getId())){
                        model=(StatisticsProductModel)result.get(product.getId());
                        model.number=model.number+orderItem.getNum();
                    }else{
                        model =new StatisticsProductModel();
                        model.number=orderItem.getNum();
                        model.productName=orderItem.getProduct().getName();
                    }
                    result.put(product.getId(),model);
                }
            }
        }
        return result;
    }

    /**
     * 找到某个产品类型在各个省的销售量
     * @param productType
     * @return
     */
    public Map<String,Object> CountByProductType(ProductType productType,Date start,Date end){
        List<Product> list = productDao.findByProductTypeAndAliveAndType(productType,1,0);//普通产品
        Map<String,Object> result =new HashMap<String,Object>();
        for(Product product:list){
            List<OrderItem> orderItems=orderItemDao.findByProductAndAliveAndCreateDate(product.getId(),1,start,end);
            for(OrderItem orderItem:orderItems){
                OrderEn order=orderItem.getOrderen();
                if(order.getState()==2){//订单已经成功
                    String address_sheng="";
                    if(order.getAddress().split(" ").length>0){
                        address_sheng=order.getAddress().split(" ")[0];
                    }
                    StatisticsProductModel model=null;
                    if(result.containsKey(address_sheng)){
                        model=(StatisticsProductModel)result.get(address_sheng);
                        model.number=model.number+orderItem.getNum();
                    }else{
                        model=new StatisticsProductModel();
                        model.productName=product.getName();
                        model.number=orderItem.getNum();
                    }
                    result.put(address_sheng,model);
                }
            }
        }
        return result;

    }

    /**
     * 统计不同省份买的各种茶叶类型的数量
     * @param start
     * @param end
     * @return
     */
    public  Map<String,HashMap<Long,Object>> CountByAllProductAndAddress(Date start ,Date end){
        List<Product> list = productDao.findByAliveAndType(1,0);//普通产品
        Map<String,HashMap<Long,Object>> result =new HashMap<String,HashMap<Long,Object>>();
        for(Product product:list){
            List<OrderItem> orderItems=orderItemDao.findByProductAndAliveAndCreateDate(product.getId(),1,start,end);
            for(OrderItem orderItem:orderItems) {
                if (orderItem.getOrderen().getState() == 2) { //订单完成才统计
                    OrderEn order=orderItem.getOrderen();
                    String address_sheng="";
                    if(order.getAddress().split(" ").length>0){
                        address_sheng=order.getAddress().split(" ")[0];
                    }
                    HashMap<Long,Object> content=null;
                    if(result.containsKey(address_sheng)){
                        content =result.get(address_sheng);
                        Long productType_id =orderItem.getProduct().getProductType().id;
                        StatisticsProductModel  model=null;
                        if(content.containsKey(productType_id)){
                            model=(StatisticsProductModel)content.get(productType_id);
                            model.number=model.number+orderItem.getNum();
                        }else{
                            model=new StatisticsProductModel();
                            model.number=orderItem.getNum();
                            model.productName=orderItem.getProduct().getProductType().name;
                        }
                        content.put(productType_id,model);
                    }else {
                        content =new HashMap<Long,Object>();
                        StatisticsProductModel model=new StatisticsProductModel();
                        model.productName=orderItem.getProduct().getProductType().name;
                        model.number=orderItem.getNum();
                        content.put(orderItem.getProduct().getProductType().id,model);
                        result.put(address_sheng,content);
                    }
                }
            }

        }
        return result;
    }

    /**
     * 统计所有茶农所有产品类型的销售量
     * @param start
     * @param end
     * @return
     */
    public Map<Long,StatisticsAllProductTypes> countAllProduct(Date start, Date end){
        List<OrderItem> list = orderItemDao.findByAlive(1);
        Map<Long,StatisticsAllProductTypes> result=new HashMap<Long,StatisticsAllProductTypes>();
        for(OrderItem orderItem:list){
            Product product=orderItem.getProduct();
            OrderEn order=orderItem.getOrderen();
            if(product.getType()==0&&order.getState()==2){//普通产品且订单已经完成
                HashMap<Long,Object> content =null;
                Long productType_id=orderItem.getProduct().getProductType().id;
                StatisticsAllProductTypes statisticsAllProductTypes=null;
                if(result.containsKey(order.getTeaSaler().getId())){//某个茶农
                    statisticsAllProductTypes=result.get(order.getTeaSaler().getId());
                    content =statisticsAllProductTypes.allproductTypes;
                    StatisticsProductModel model=null;
                    if(content.containsKey(productType_id)){
                        model=(StatisticsProductModel)content.get(productType_id);
                        model.number=model.number+orderItem.getNum();
                    }else{
                        model=new StatisticsProductModel();
                        model.productName=orderItem.getProduct().getProductType().name;
                        model.number=orderItem.getNum();
                    }
                    content.put(productType_id,model);
                    statisticsAllProductTypes.allproductTypes=content;
                }else {
                    statisticsAllProductTypes=new StatisticsAllProductTypes();
                    content =new HashMap<Long,Object>();
                    StatisticsProductModel model=new StatisticsProductModel();
                    model=new StatisticsProductModel();
                    model.productName=orderItem.getProduct().getProductType().name;
                    model.number=orderItem.getNum();
                    content.put(productType_id,model);
                    statisticsAllProductTypes.allproductTypes=content;
                    statisticsAllProductTypes.teaSalerName=orderItem.getOrderen().getTeaSaler().getName();
                }
                result.put(orderItem.getOrderen().getTeaSaler().getId(),statisticsAllProductTypes);
            }
        }
        return result;
    }

    public Map<Long,Object> countTeaSalerAllProductType(TeaSaler teaSaler, Date start, Date end){
        Map<Long,Object> result =new HashMap<Long,Object>();
        List<OrderEn> list = orderEnDao.findByTeaSalerAndAliveAndCreateDate(teaSaler.getId(),1,start,end);
        for(OrderEn order:list){
            List<OrderItem> orderItems =orderItemDao.findByOrderenAndAlive(order,1);
            for(OrderItem orderItem:orderItems){
                if(orderItem.getOrderen().getState()==2){ //订单成功才统计
                    Product product=orderItem.getProduct();
                    StatisticsProductModel model =null;
                    Long productType_id =product.getProductType().id;
                    if(result.containsKey(productType_id)){
                        model=(StatisticsProductModel)result.get(productType_id);
                        model.number=model.number+orderItem.getNum();
                    }else{
                        model =new StatisticsProductModel();
                        model.number=orderItem.getNum();
                        model.productName=orderItem.getProduct().getProductType().name;
                    }
                    result.put(productType_id,model);
                }
            }
        }
        return result;
    }
}
