package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ycc on 16/11/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 产品
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * 订单项
     */
    @ManyToOne
    @JoinColumn(name = "orderItem_id")
    private OrderItem orderItem;

    /**
     * 消费者
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    /**
     * 评论内容
     */
    @Column
    private String content="";

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 评价时间
     */
    @Column
    private Date createDate;

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

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    /**
     * 评分内容

     */
    @Column
    private double score=100;

    @Column
    private int alive=1;

}
