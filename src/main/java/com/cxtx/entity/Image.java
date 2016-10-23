package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by ycc on 16/10/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    /**
     * 图片名字
     */
    @Column
    public String name;

    /**
     * 路径
     */
    @Column
    public String url;

    /**
     * 所属的产品
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    /**
     * 是否存在
     */
    @Column
    public int alive=1;

}
