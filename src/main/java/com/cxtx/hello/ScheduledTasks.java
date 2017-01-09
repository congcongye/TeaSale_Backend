package com.cxtx.hello;

import com.cxtx.model.TeaModel;
import com.cxtx.predictor.Predictor;
import com.cxtx.service.CrowdFundingService;
import com.cxtx.service.CrowdSourcingService;

import jxl.read.biff.BiffException;

import com.cxtx.service.impl.Recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jinchuyang on 16/12/6.
 */
@Component
public class ScheduledTasks {
    @Autowired
    private CrowdFundingService crowdFundingService;
    @Autowired
    private CrowdSourcingService crowdSourcingService;
    @Autowired
    private Recommend recommend;

    @Scheduled(fixedRate = 5000000)
    public void reportCurrentTime() {
        crowdFundingService.checkNum();
        crowdFundingService.checkIsFinish();
        crowdSourcingService.checkNum();
//        recommend.deleteFile();
    }

    /**
     * 每天24点00分00秒时执行,商品价格预测
     * @throws IOException
     * @throws BiffException
     */
    @Scheduled(cron = "00 00 00 * * ?")
    public void timerCron() throws IOException, BiffException {
        System.out.print("定时任务开始");
        Predictor predictor = new Predictor();

        List<List<TeaModel>> datas = predictor.Predicte();
        List<TeaModel> list = new ArrayList<TeaModel>();
        for (List<TeaModel> teaModelList : datas){
            for (TeaModel teaModel : teaModelList){
                list.add(teaModel);
            }
        }
        String json = com.alibaba.fastjson.JSON.toJSONString(list,true);
        File file = new File("src/main/resources/price.properties");
        if (!file.exists()){
            file.createNewFile();
        }
        //System.out.println(file.exists());
        FileOutputStream oFile = new FileOutputStream(file);
        Properties properties = new Properties();
        properties.setProperty("price",json);
        properties.store(oFile,"predicate price");
        //oFile.flush();
        oFile.close();
    }

    /**
     * 凌晨4点的时候,重新进行用户商品的推荐
     * @throws IOException
     * @throws BiffException
     */
    @Scheduled(cron = "00 00 04 * * ?")//00 00 00 * * ?
    public void recommendTime() throws IOException, BiffException {
        recommend.deleteFile();
    }
}
