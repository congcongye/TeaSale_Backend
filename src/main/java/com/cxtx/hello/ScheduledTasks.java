package com.cxtx.hello;

import com.cxtx.service.CrowdFundingService;
import com.cxtx.service.CrowdSourcingService;
import com.cxtx.service.impl.Recommend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        recommend.deleteFile();
    }
}
