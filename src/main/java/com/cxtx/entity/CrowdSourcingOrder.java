package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ycc on 16/12/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CROWDSOURCINGORDER")
public class CrowdSourcingOrder {

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
     * 订单状态 0 未完成, 1已付款,2已完成
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
    private double score = 0;// 分数


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
}
