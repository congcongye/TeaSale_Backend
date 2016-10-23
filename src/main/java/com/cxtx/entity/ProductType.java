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
     * 是否可以使用 1可用,0不可用
     */
    @Column
    public int state=1;

    /**
     * 是否存在(不能进行更改,只是为了和整体保持一致,变成软删除)
     */
    @Column
    public int alive=1;
}
