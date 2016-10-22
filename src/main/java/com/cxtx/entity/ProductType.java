package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by ycc on 16/10/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "PRODUCTTYPE")
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    /**
     * 产品名称
     */
    @Column
    public String name;

    /**
     * 产品描述
     */
    @Column
    public String descript;

    /**
     * 图片的链接
     */
    @Column
    public String url;

    /**
     * 是否存在
     */
    @Column
    public int alive=1;
}
