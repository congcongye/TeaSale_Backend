package com.cxtx.entity;

import com.cxtx.model.UpdateOrderModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinchuyang on 16/12/6.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CROWDFUNDINGORDER")
public class CrowdFundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date createDate;//创建时间

    @ManyToOne
    @JoinColumn(name = "teaSaler_id")
    private TeaSaler teaSaler;//茶农

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer ;//消费者

    @ManyToOne
    @JoinColumn(name = "crowdFunding_id")
    private CrowdFunding crowdFunding;

    @Column
    private String name;// 收件人名字

    @Column
    private String address ;//收货地址

    @Column
    private String zip ;//邮编

    @Column
    private String tel ;//电话

    @Column
    private double totalPrice ;// 总额

    @Column
    private double logistic ;//邮费

    @Column
    private int num;//份数

    /**
     * 订单状态 0 未完成, 1已付款,2已完成,3取消
     */
    @Column
    private int state = 0;

    @Column
    private int isSend = 0;// 是否发货

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date SendDate ;//发货时间

    @Column
    private int isConfirm = 0;//是否确认收货

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date confirmDate;// 确认时间

    @Column
    private double score = -1;// 分数


    /**
     * (未支付，全支付，部分支付)(0, 1, 2)
     */
    @Column
    private int Refund_state;

    @Column
    private int customerDelete = 0;//消费者不想看

    @Column
    private int adminDelete = 0;// 管理员不想看

    @Column
    private int salerDelete = 0;//茶农不想看

    @Column
    private int  alive ;//是否删除

    @Column
    private double hasPay;//已付金额

    public String getWuliu() {
        return wuliu;
    }

    public void setWuliu(String wuliu) {
        this.wuliu = wuliu;
    }

    @Column
    private String wuliu="";

    public double getHasPay() {
        return hasPay;
    }

    public void setHasPay(double hasPay) {
        this.hasPay = hasPay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public TeaSaler getTeaSaler() {
        return teaSaler;
    }

    public void setTeaSaler(TeaSaler teaSaler) {
        this.teaSaler = teaSaler;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CrowdFunding getCrowdFunding() {
        return crowdFunding;
    }

    public void setCrowdFunding(CrowdFunding crowdFunding) {
        this.crowdFunding = crowdFunding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getLogistic() {
        return logistic;
    }

    public void setLogistic(double logistic) {
        this.logistic = logistic;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public Date getSendDate() {
        return SendDate;
    }

    public void setSendDate(Date sendDate) {
        SendDate = sendDate;
    }

    public int getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(int isConfirm) {
        this.isConfirm = isConfirm;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRefund_state() {
        return Refund_state;
    }

    public void setRefund_state(int refund_state) {
        Refund_state = refund_state;
    }


    public int getCustomerDelete() {
        return customerDelete;
    }

    public void setCustomerDelete(int customerDelete) {
        this.customerDelete = customerDelete;
    }

    public int getAdminDelete() {
        return adminDelete;
    }

    public void setAdminDelete(int adminDelete) {
        this.adminDelete = adminDelete;
    }

    public int getSalerDelete() {
        return salerDelete;
    }

    public void setSalerDelete(int salerDelete) {
        this.salerDelete = salerDelete;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


}

