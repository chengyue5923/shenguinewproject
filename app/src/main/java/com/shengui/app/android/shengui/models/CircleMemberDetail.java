package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/1/12.
 */

public class CircleMemberDetail implements Serializable {

    String id;
    String name;
    String mobile;
    String truename;
    String location;
    String sex;
    String avatar;
    String name_first;
    String name_py;
    String last_ip;
    String create_time;
    boolean isflag=false;

    public boolean isflag() {
        return isflag;
    }

    public void setIsflag(boolean isflag) {
        this.isflag = isflag;
    }

    String is_attention;

    public String getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(String is_attention) {
        this.is_attention = is_attention;
    }

    @Override
    public String toString() {
        return "CircleMemberDetail{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", truename='" + truename + '\'' +
                ", location='" + location + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name_first='" + name_first + '\'' +
                ", name_py='" + name_py + '\'' +
                ", last_ip='" + last_ip + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName_first() {
        return name_first;
    }

    public void setName_first(String name_first) {
        this.name_first = name_first;
    }

    public String getName_py() {
        return name_py;
    }

    public void setName_py(String name_py) {
        this.name_py = name_py;
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
}
