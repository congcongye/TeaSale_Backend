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
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    /**
     * 所属产品类型
     */
    @ManyToOne
    @JoinColumn(name = "productType_id")
    public ProductType productType;

    /**
     * 描述
     */
    @Column
    public String remark;

    /**
     * 产品名称
     */
    @Column
    public String name;

    /**
     * 产品级别
     */
    @Column
    public int level;

    /**
     * 产地
     */
    @Column
    public String locality;

    /**
     * 库存
     */
    @Column
    public double stock;

    /**
     * 单价
     */
    @Column
    public double price;

    /**
     * 起售数量
     */
    @Column
    public double startNum;

    /**
     * 折扣
     */
    @Column
    public double discount;

    /**
     * 是否包邮,1包邮 0不包邮
     */
    @Column
    public int isFree=1;

    /**
     * 邮费
     */
    @Column
    public double postate;

    /**
     * 发货间隔 (5天或者10天等)
     */
    @Column
    public int deliverLimit;

    /**
     * 产品的上架时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    public Date createDate;

    /**
     * 计数单位
     */
    @Column
    public String unit;

    /**
     * 所属的茶农
     */
    @ManyToOne
    @JoinColumn(name = "teaSeller_id")
    public TeaSeller teaSeller;

    /**
     * 商品状态,0未上架  1上架
     */
    @Column
    public int state=0;

    /**
     * 是否存在
     */
    @Column
    public int alive=1;
}
