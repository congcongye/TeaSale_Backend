package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by ycc on 16/10/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ORDERITEM")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;//商品

    @ManyToOne
    @JoinColumn(name = "orderen_id")
    private OrderEn orderen ;//对应订单

    @Column
    private double num;// 商品数量

    @Column
    private double totalPrice;// 总价

    @Column
    private int  alive ;//是否删除

    /**
     * 0未评论  1评论
     */
    @Column
    private int isComment=0;//是否评价

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }



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

    public OrderEn getOrderen() {
        return orderen;
    }

    public void setOrderen(OrderEn orderen) {
        this.orderen = orderen;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
