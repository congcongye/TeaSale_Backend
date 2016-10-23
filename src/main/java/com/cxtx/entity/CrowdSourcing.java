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

    @Column
    private int state;


}
