package com.cxtx.controller;

import com.cxtx.dao.ProductTypeDao;
import com.cxtx.dao.TeaSalerDao;
import com.cxtx.entity.Product;
import com.cxtx.entity.ProductType;
import com.cxtx.entity.TeaSaler;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.impl.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    @RequestMapping(value = "/statistics/teasalerProduct", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult TeaSalerSearch(@RequestParam(value = "teaSaler_id",defaultValue = "-1")Long teaSaler_id, @RequestParam(value = "startDate",defaultValue ="")String start,@RequestParam(value = "endDate",defaultValue ="")String end){
        TeaSaler teaSaler =teaSalerDao.findByIdAndStateAndAlive(teaSaler_id,1,1);
        if(teaSaler==null){
            return ServiceResult.fail(500, "TeaSaler is Illegal");
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
        Map<Long,Object> result =statisticsService.CountByTeaSalerAndProductAndDate(teaSaler,startDate,endDate);
        return ServiceResult.success(result);
    }

    @RequestMapping(value = "/statistics/addressSearch", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult AddressSearch(@RequestParam(value = "productType_id",defaultValue = "-1")Long productType_id, @RequestParam(value = "startDate",defaultValue ="")String start,@RequestParam(value = "endDate",defaultValue ="")String end){
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


}
