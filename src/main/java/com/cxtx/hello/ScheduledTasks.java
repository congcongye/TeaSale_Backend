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

import java.io.*;
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

    @Scheduled(fixedRate = 6000000)
    public void reportCurrentTime() {
        crowdFundingService.checkNum();
        crowdFundingService.checkIsFinish();
        crowdSourcingService.checkNum();
//        recommend.deleteFile();
    }

    /**
     * 每天24点00分00秒时执行
     * @throws IOException
     * @throws BiffException
     */
    @Scheduled(cron = "00 40 19 * * ?")
    public void timerCron() throws IOException, BiffException {
        Predictor predictor = new Predictor();

        List<List<TeaModel>> datas = predictor.Predicte();
        List<TeaModel> list = new ArrayList<TeaModel>();
        for (List<TeaModel> teaModelList : datas){
            for (TeaModel teaModel : teaModelList){
                list.add(teaModel);
            }
        }
        String json = com.alibaba.fastjson.JSON.toJSONString(list,true);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("cxtx.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String folderPath = p.getProperty("predicateFile");
        File file=new File(folderPath);
        if (!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(json);
        bufferedWriter.flush();
        bufferedWriter.close();
//        //System.out.println(file.exists());
//        FileOutputStream oFile = new FileOutputStream(file);
//        Properties properties = new Properties();
//        properties.setProperty("price",json);
//        properties.store(oFile,"predicate price");
//        //oFile.flush();
//        oFile.close();
    }
}
