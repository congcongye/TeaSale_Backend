package com.cxtx.hello;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        crowdFundingService.checkNum();
        crowdSourcingService.checkNum();
//        recommend.deleteFile();
    }

    /**
     * 每天24点00分00秒时执行
     * @throws IOException
     * @throws BiffException
     */
    @Scheduled(cron = "50 27 23 * * ?")
    public void timerCron() throws IOException, BiffException {
        //System.out.println("current time : "+ sdf.format(new Date()));
        DecimalFormat df   = new DecimalFormat("######0.00");
        String[] types ={"West Lake Longjing","Tieguanyin","Biluochun"};
        Map<String, Double> result = new HashMap<String, Double>();
        Predictor predictor = new Predictor();
        for (String type : types) {
            double price = predictor.Predicte(type);
            result.put(type, Double.parseDouble(df.format(price)));
        }
        String json = com.alibaba.fastjson.JSON.toJSONString(result,true);
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
}
