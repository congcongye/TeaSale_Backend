package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jinchuyang on 16/10/21.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "TEASALER")
public class TeaSaler{
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

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
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
    @Column
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
     * 是否审核通过(state=0未审核通过,state=1审核通过,state=2被管理在此审核不通过)
     */
    @Column
    private int state=0;
    /**
     * 是否删除
     */
    @Column
    private int alive=1;

    /**
     * 创建时间
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    @Column
    private Date createDate;

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


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

//    public String toString(){
//        return "id: "+this.getId()+",nickname:"+this.getNickname()+",headurl:"+this.getHeadUrl();
//    }

}
