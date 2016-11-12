package com.cxtx.model;

/**
 * Created by jinchuyang on 16/10/26.
 */
public class CreateManagerModel {
    private String tel;
    private String password;
    private String name;
    private int level;
//    private double money;
//    private String headUrl;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
