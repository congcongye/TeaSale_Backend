package com.cxtx.controller;

import com.cxtx.entity.CrowdFunding;
import com.cxtx.model.IdModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.service.CrowdFundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ycc on 16/11/26.
 */
@Controller
public class CrowdFundingController extends ApiController{

    @Autowired
    private CrowdFundingService crowdFundingService;

    /**
     * 发起众包
     * @param crowdFunding
     * @return
     */
    @RequestMapping(value = "/crowdFund/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult newCrowdFund(@RequestBody CrowdFunding crowdFunding){
         checkParameter(crowdFunding!=null,"data is empty");
         if(crowdFunding.getProduct()==null){
             return ServiceResult.fail(500, "product is empty!");
         }
         CrowdFunding result = crowdFundingService.newCrowdFunding(crowdFunding);
         if(result==null){
             return ServiceResult.fail(500, "crowdFunding repeat!");
         }
         return  ServiceResult.success(result);
    }

    @RequestMapping(value = "/crowdFund/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateCrowdFund(@RequestBody CrowdFunding crowdFunding){
        checkParameter(crowdFunding!=null,"data is empty");
        if(crowdFunding.getProduct()==null){
            return ServiceResult.fail(500, "product is empty!");
        }
        CrowdFunding result = crowdFundingService.updateCrowdFunding(crowdFunding);
        if(result==null){
            return ServiceResult.fail(500, "crowdFunding can't be update!");
        }
        return  ServiceResult.success(result);
    }

    @RequestMapping(value = "/crowdFund/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateCrowdFund(@RequestBody List<IdModel> list){
        checkParameter(list!=null,"data is empty");
        checkParameter(!list.isEmpty(),"data is empty");
        int succCount = crowdFundingService.deleteCrowdFunding(list);
        if(succCount!=list.size()){
            return ServiceResult.fail(500, "the num of succeed is "+succCount+" ; the fail number is "+(list.size()-succCount));
        }
        return ServiceResult.success("all succeed!");
    }

    @RequestMapping(value = "/crowdFund/search", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult searchCrowdFunding(@RequestParam(value = "product_id",defaultValue = "-1")Long product_id,@RequestParam(value = "teaSaler_id",defaultValue = "-1")Long teaSaler_id,@RequestParam(value = "type",defaultValue = "-1")int type,@RequestParam(value = "lowEarnest",defaultValue ="-1")double lowEarnest,
                                            @RequestParam(value = "highEarnest",defaultValue = "-1")double highEarnest,@RequestParam(value = "lowUnitNum",defaultValue = "-1")double lowUnitNum,@RequestParam(value = "highUnitNum",defaultValue = "-1")double highUnitNum,
                                            @RequestParam(value = "lowUnitMoney",defaultValue = "-1")double lowUnitMoney,@RequestParam(value = "highUnitMoney",defaultValue = "-1")double highUnitMoney,@RequestParam(value = "state",defaultValue = "-1")int state,
                                            @RequestParam(value = "lowRemainderNum",defaultValue = "-1")double lowRemainderNum,@RequestParam(value = "highRemainderNum",defaultValue = "-1")double highRemainderNum) {
        List<CrowdFunding> list =crowdFundingService.searchCrowdFunding(product_id,teaSaler_id,type,lowEarnest,highEarnest,lowUnitNum,highUnitNum,lowUnitMoney,highUnitMoney,state,lowRemainderNum,highRemainderNum);
        return ServiceResult.success(list);
    }



}
