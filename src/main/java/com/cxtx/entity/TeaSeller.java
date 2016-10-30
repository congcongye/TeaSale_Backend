package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by jinchuyang on 16/10/21.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TEASELLER")
public class TeaSeller {
    /**
     licenseUrl
     zip 邮编


     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 真实名字
     */
    @Column
    private String name;

    /**
     * 级别
     */
    @Column
    private int level;

    /**
     * 昵称
     */
    @Column
    private String nickname;
    /**
     * 密码
     */
    @Column
    private String password;
    /**
     * 地址
     */
    @Column
    private String address;
    /**
     * 电话
     */
    @Column
    private String tel;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 余额
     */
    @Column
    private double money;
    /**
     * 营业执照图片
     */
    private String licenseUrl;
    /**
     * 邮编
     */
    @Column
    private String zip;
    /**
     * 身份证号
     */
    @Column
    private String idCard;

    /**
     * 是否审核通过
     */
    @Column
    private int state=1;
    /**
     * 是否删除
     */
    @Column
    private int alive=1;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setLevel(int lever) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public int getState() {
        return state;

    }

    public int getAlive() {
        return alive;
    }
}
