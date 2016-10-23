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
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "Customer_id")
    public Customer customer;//消费者

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;//产品

    @Column
    public double num ;//产品数量

    @Column
    public double price ;//加入时单价

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    public Date joinDate ;//加入时间

    @Column
    public int alive ;//是否删除
}
