package com.cxtx.service.impl;

import com.cxtx.dao.OrderEnDao;
import com.cxtx.dao.OrderItemDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.*;
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
                String address_sheng="";
                if(order.getAddress().split(" ").length>0){
                    address_sheng=order.getAddress().split(" ")[0];
                }
                StatisticsProductModel model=null;
                if(result.containsKey(address_sheng)){
                    model=(StatisticsProductModel)result.get(address_sheng);
                    model.number=model.number+orderItem.getNum();
                }else{
                    model.productName=orderItem.getProduct().getName();
                    model.number=orderItem.getNum();
                }
                result.put(address_sheng,model);
            }
        }
        return result;

    }
}
