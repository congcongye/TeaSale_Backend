package com.cxtx.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by ycc on 16/11/26.
 */
public class UpdateCrowdFundingModel {
    public Long id;
    public int type=0;
    public double earnest=0;
    public double unitNum=0;
    public double unitMoney=0;
    public int joinNum=0;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date dealDate=null;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public  Date deliverDate=null;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date payDate=null;
    public double totalNum=0;


}
