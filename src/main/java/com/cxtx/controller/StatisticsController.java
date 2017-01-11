package com.cxtx.controller;

import com.cxtx.dao.CustomerDao;
import com.cxtx.dao.ProductTypeDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Customer;
import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.ProductNumModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.StatisticsAllProductTypes;
import com.cxtx.service.impl.Recommend;
import com.cxtx.service.impl.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ycc on 17/1/3.
 */
@Controller
public class StatisticsController extends ApiController{

    @Autowired
    private StatisticsServiceImpl statisticsService;
    @Autowired
    private TeaSalerDao teaSalerDao;
    @Autowired
    private ProductTypeDao productTypeDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private Recommend recommend;

    @RequestMapping(value = "/statistics/teasalerProduct", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult TeaSalerSearch(@RequestParam(value = "teaSaler_id",defaultValue = "-1")Long teaSaler_id, @RequestParam(value = "startDate",defaultValue ="")String start,@RequestParam(value = "endDate",defaultValue ="")String end){
        TeaSaler teaSaler =teaSalerDao.findByIdAndStateAndAlive(teaSaler_id,1,1);
        if(teaSaler==null){
            return ServiceResult.fail(500, "茶农审核未通过");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate=null;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            return ServiceResult.fail(500, "日期格式错误");
        }
        Map<Long,Object> result =statisticsService.CountByTeaSalerAndProductAndDate(teaSaler,startDate,endDate);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/statistics/addressSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult AddressSearch(@RequestParam(value = "productType_id",defaultValue = "-1")Long productType_id,
                                                     @RequestParam(value = "startDate",defaultValue ="")String start,@RequestParam(value = "endDate",defaultValue ="")String end){
        ProductType productType=productTypeDao.findByIdAndAlive(productType_id,1);
        if(productType==null){
            return ServiceResult.fail(500, "productType don't exist");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate=null;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            return ServiceResult.fail(500, "time is error");
        }
        Map<String,Object> result=statisticsService.CountByProductType(productType,startDate,endDate);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/statistics/searchAllProducts", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult AddressSearch(@RequestParam(value = "startDate",defaultValue ="")String start,@RequestParam(value = "endDate",defaultValue ="")String end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate=null;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            return ServiceResult.fail(500, "日期格式错误");
        }
        Map<String,HashMap<Long,Object>> result =statisticsService.CountByAllProductAndAddress(startDate,endDate);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/statistics/allproductTypes", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult AllProductTypes(@RequestParam(value = "startDate",defaultValue ="")String start,@RequestParam(value = "endDate",defaultValue ="")String end){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate=null;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            return ServiceResult.fail(500, "日期格式错误");
        }
        Map<Long,StatisticsAllProductTypes> result =statisticsService.countAllProduct(startDate,endDate);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/statistics/teaSalerAllProductTypes", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult TeaSalerAllProductTypes(@RequestParam(value = "teaSaler_id",defaultValue = "-1")Long teaSaler_id, @RequestParam(value = "startDate",defaultValue ="")String start,@RequestParam(value = "endDate",defaultValue ="")String end){
        TeaSaler teaSaler =teaSalerDao.findByIdAndStateAndAlive(teaSaler_id,1,1);
        if(teaSaler==null){
            return ServiceResult.fail(500, "茶农未审核通过");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate=null;
        try {
            startDate = sdf.parse(start);
            endDate = sdf.parse(end);
        } catch (ParseException e) {
            return ServiceResult.fail(500, "日期格式错误");
        }
        Map<Long,Object> result =statisticsService.countTeaSalerAllProductType(teaSaler,startDate,endDate);
        return ServiceResult.success(result);
    }

    /**
     * 协同过滤的商品推荐,先找用户行为相似度高的前5个,然后找他们买的商品,进行推荐
     * @param customer_id
     * @return
     */
    @RequestMapping(value = "/statistics/recommend", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult RecommendProducts(@RequestParam(value = "customer_id",defaultValue = "-1")Long customer_id){
        Customer customer =customerDao.findByIdAndAlive(customer_id,1);
        if(customer==null){
            return ServiceResult.fail(500,"该消费者不存在");
        }
        Map<Long,ProductNumModel> result =recommend.getSimilarity(customer);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/statistics/newrecommend", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult newRecommendProducts(@RequestParam(value = "customer_id",defaultValue = "-1")Long customer_id){
        Customer customer =customerDao.findByIdAndAlive(customer_id,1);
        if(customer==null){
            return ServiceResult.fail(500,"该消费者不存在");
        }
        Map<String,Object> result= null;
        try {
            result = recommend.getAllSimilarity(customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/statistics/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ServiceResult deleteRecommendFile(){
        recommend.deleteFile();
        return ServiceResult.success("删除成功");
    }
    @RequestMapping(value = "/statistics/delete2", method = RequestMethod.DELETE)
    @ResponseBody
    public ServiceResult deleteFile(){
        recommend.deleteCountSimFile();
        return ServiceResult.success("删除成功");
    }

    @RequestMapping(value = "/statistics/countSim", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult countSim(){
        Map<String,Object> result=null;
        try {
            result =recommend.countSim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result!=null){
           return  ServiceResult.success(result);
        }else{
            return ServiceResult.fail(500,"网络错误");
        }
    }


}
