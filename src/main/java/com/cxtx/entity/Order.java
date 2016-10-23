package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ycc on 16/10/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    public Date createDate;//创建时间

    @ManyToOne
    @JoinColumn(name = "teaSeller_id")
    public TeaSeller teaSeller ;//茶农

    @ManyToOne
    @JoinColumn(name = "customer_id")
    public Customer customer ;//消费者

    @Column
    public String name;// 收件人名字

    @Column
    public String address ;//收货地址

    @Column
    public String zip ;//邮编

    @Column
    public String tel ;//电话

    @Column
    public double totalPrice ;// 总额

    @Column
    public double logistic ;//邮费

    @Column
    public int state;// 订单状态

    @Column
    public int isSend;// 是否发货

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    public Date SendDate ;//发货时间

    @Column
    public int isConfirm;//是否确认收货

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    public Date confirmDate;// 确认时间

    @Column
    public int isComment ;//是否评价
    @Column
    public double score;// 分数
    @Column
    public String comment ;//评价
    @Column
    public int Refund_state;//(未支付，全支付，部分支付)
    @Column
    public int type;//  订单类型（一般，众筹，众包）
    @Column
    public int customerDelete ;//消费者不想看
    @Column
    public int adminDelete;// 管理员不想看
    @Column
    public int sellerDelete;//茶农不想看
}
