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
    private Long id;

    /**
     * 所属产品类型
     */
    @ManyToOne
    @JoinColumn(name = "productType_id")
    private ProductType productType;

    /**
     * 描述
     */
    @Column
    private String remark;

    /**
     * 产品名称
     */
    @Column
    private String name;

    /**
     * 产品级别
     */
    @Column
    private int level;
    /**
     * 产地
     */
    @Column
    private String locality;

    /**
     * 库存
     */
    @Column
    private double stock;

    /**
     * 单价
     */
    @Column
    private double price;

    /**
     * 起售数量
     */
    @Column
    private double startNum;

    /**
     * 折扣
     */
    @Column
    private double discount;

    /**
     * 是否包邮,1包邮 0不包邮
     */
    @Column
    private int isFree=1;

    /**
     * 邮费
     */
    @Column
    private double postate;

    /**
     * 发货间隔 (5天或者10天等)
     */
    @Column
    private int deliverLimit;

    /**
     * 产品的上架时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date createDate;

    /**
     * 计数单位
     */
    @Column
    private String unit;

    /**
     * 所属的茶农
     */
    @ManyToOne
    @JoinColumn(name = "teaSeller_id")
    private TeaSeller teaSeller;

    /**
     * 商品状态,0未上架  1上架
     */
    @Column
    private int state=0;

    /**
     * 是否存在
     */
    @Column
    private int alive=1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStartNum() {
        return startNum;
    }

    public void setStartNum(double startNum) {
        this.startNum = startNum;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public double getPostate() {
        return postate;
    }

    public void setPostate(double postate) {
        this.postate = postate;
    }

    public int getDeliverLimit() {
        return deliverLimit;
    }

    public void setDeliverLimit(int deliverLimit) {
        this.deliverLimit = deliverLimit;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public TeaSeller getTeaSeller() {
        return teaSeller;
    }

    public void setTeaSeller(TeaSeller teaSeller) {
        this.teaSeller = teaSeller;
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
