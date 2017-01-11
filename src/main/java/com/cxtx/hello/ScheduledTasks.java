package com.cxtx.hello;

import com.cxtx.model.TeaModel;
import com.cxtx.predictor.PredicateUtils;
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

    @Scheduled(cron ="0/60 * * * * ?}")
    public void reportCurrentTime() {
        crowdFundingService.checkNum();
        crowdFundingService.checkIsFinish();
        crowdSourcingService.checkNum();
        crowdSourcingService.checkIsFinish();
        crowdSourcingService.addCustomerMoney();
    }

    /**
     * 每天24点00分00秒时执行,商品价格预测
     * @throws IOException
     * @throws BiffException
     */
    @Scheduled(cron = "00 20 20 * * ?")
    public void timerCron() throws IOException, BiffException {

        new PredicateUtils().doPredicate();

    }

    /**
     * 凌晨4点的时候,重新进行用户商品的推荐
     * @throws IOException
     * @throws BiffException
     */
    @Scheduled(cron = "00 00 04 * * ?")//00 00 00 * * ?
    public void recommendTime() throws IOException, BiffException {
        recommend.deleteFile();
        recommend.deleteCountSimFile();

    }
}
