package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinchuyang on 16/10/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CROWDSOURCING")
public class CrowdSourcing {

    /**
     *

     createDate（开始时间）
     dealDate（众筹结束时间）
     deliverDate（开始发货时间）
     state
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;//产品

    @ManyToOne
    @JoinColumn(name = "Customer_id")
    public Customer customer;//消费者;

    @Column
    private double earnest;

    @Column
    private double unitNum;

    @Column
    private double unitMoney;

    @Column
    private int JoinNum;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date createDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date dealDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private  Date deliverDate;

    /**
     * 0  创建
     * 1  成功(关闭)
     * 2  未完成(关闭)
     * 3  成功(未完成,需通知茶农发货)
     */
    @Column
    private int state=0;

    @Column
    private int alive=1;
    @Column
    private double totalNum;

    public double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(double totalNum) {
        this.totalNum = totalNum;
    }

    public double getRemainderNum() {
        return remainderNum;
    }

    public void setRemainderNum(double remainderNum) {
        this.remainderNum = remainderNum;
    }

    @Column

    private double remainderNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getEarnest() {
        return earnest;
    }

    public void setEarnest(double earnest) {
        this.earnest = earnest;
    }

    public double getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(double unitNum) {
        this.unitNum = unitNum;
    }

    public double getUnitMoney() {
        return unitMoney;
    }

    public void setUnitMoney(double unitMoney) {
        this.unitMoney = unitMoney;
    }

    public int getJoinNum() {
        return JoinNum;
    }

    public void setJoinNum(int joinNum) {
        JoinNum = joinNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
