package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by yanbo on 2016/7/22.
 */
public class LoginResultModel implements Serializable {
    int id;
    String name;
    String mobile;
    String truename;
    String token;
    String location;
    int sex;
    String avatar;
    String last_ip;
    String create_time;
    String qrcode;
    String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "LoginResultModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", truename='" + truename + '\'' +
                ", token='" + token + '\'' +
                ", location='" + location + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", last_ip='" + last_ip + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
