package com.cxtx.controller;

import com.cxtx.dao.CrowdFundingDao;
import com.cxtx.dao.ProductDao;
import com.cxtx.entity.CrowdFunding;
import com.cxtx.entity.Product;
import com.cxtx.model.IdModel;
import com.cxtx.model.ServiceResult;
import com.cxtx.model.UpdateCrowdFundingModel;
import com.cxtx.service.CrowdFundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CrowdFundingDao crowdFundingDao;

    /**
     * 发起众包
     * @param crowdFunding
     * @return
     */
    @RequestMapping(value = "/crowdFund/new", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult newCrowdFund(@RequestBody CrowdFunding crowdFunding,@RequestParam(value="product_id",defaultValue = "-1")Long product_id){
         checkParameter(crowdFunding!=null,"data is empty");
         Product product =productDao.findByIdAndAlive(product_id,1);
         if(product==null){
             return ServiceResult.fail(500, "product is empty!");
         }
         crowdFunding.setProduct(product);
         CrowdFunding result = crowdFundingService.newCrowdFunding(crowdFunding);
         if(result==null){
             return ServiceResult.fail(500, "crowdFunding repeat!");
         }
         return  ServiceResult.success(result);
    }

    @RequestMapping(value = "/crowdFund/update", method = RequestMethod.PUT)
    @ResponseBody
    public ServiceResult updateCrowdFund(@RequestBody UpdateCrowdFundingModel updateCrowdFundingModel){
        checkParameter(updateCrowdFundingModel!=null,"data is empty");
        CrowdFunding crowdFunding =crowdFundingDao.findByIdAndAlive(updateCrowdFundingModel.id,1);
        if(crowdFunding==null){
            return ServiceResult.fail(500, "old crowdFunding is empty!");
        }
        if(crowdFunding.getProduct()==null){
            return ServiceResult.fail(500, "product is empty!");
        }
        CrowdFunding result = crowdFundingService.updateCrowdFunding(updateCrowdFundingModel);
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
                                            @RequestParam(value = "lowRemainderNum",defaultValue = "-1")double lowRemainderNum,@RequestParam(value = "highRemainderNum",defaultValue = "-1")double highRemainderNum,@RequestParam (value = "productType_name",defaultValue = "")String productType_name,
                                            @RequestParam (value = "product_name",defaultValue = "")String product_name,@RequestParam (value = "productType_id",defaultValue = "")Long productType_id, @RequestParam(value="pageIndex", defaultValue="0") int pageIndex, @RequestParam(value="pageSize", defaultValue="10") int pageSize, @RequestParam(value="sortField", defaultValue="id") String sortField, @RequestParam(value="sortOrder", defaultValue="ASC") String sortOrder) {
        Page<CrowdFunding> page =crowdFundingService.searchCrowdFunding(product_id,teaSaler_id,type,lowEarnest,highEarnest,lowUnitNum,highUnitNum,lowUnitMoney,highUnitMoney,state,lowRemainderNum,highRemainderNum,productType_id,productType_name,product_name,pageIndex,pageSize, sortField,sortOrder);
        return ServiceResult.success(page);
    }



}
