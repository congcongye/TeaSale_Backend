package com.cxtx.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by ycc on 16/12/22.
 */
public class CrowdSourcingModel {
    public double earnest;
    public double unitNum;
    public double unitMoney;
    public int joinNum;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date createDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date dealDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    public Date deliverDate;
    /**
     * 0初始状态  1成功
     */
    public int state=0;

    /**
     * 总量
     */
    public double totalNum;

    /**
     * 剩余总量
     */
    public double remainderNum;

}
