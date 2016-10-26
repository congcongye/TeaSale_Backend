package com.cxtx.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jinchuyang on 16/10/26.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "ACCOUNT")
public class Account {
    /**
     * 电话
     */
    @Column
    private String tel;

    /**
     * 密码
     */
    @Column
    private String password;

    @Column
    private int label;

    @Column
    private int alive;


    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }
}
