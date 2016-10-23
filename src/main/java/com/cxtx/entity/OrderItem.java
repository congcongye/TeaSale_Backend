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
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;//商品

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Order order ;//对应订单

    @Column
    public double num;// 商品数量

    @Column
    public double totalPrice;// 总价

    @Column
    public int  alive ;//是否删除
}
