package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/12.
 */

public class FansOrAttentionModel implements Serializable{

    int  id;
    String name;
    String mobile;
    String truename;
    String location;
    int  sexl;
    String avatar;
    String name_first;
    String name_py;
    String create_time;

    @Override
    public String toString() {
        return "FansOrAttentionModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", truename='" + truename + '\'' +
                ", location='" + location + '\'' +
                ", sexl=" + sexl +
                ", avatar='" + avatar + '\'' +
                ", name_first='" + name_first + '\'' +
                ", name_py='" + name_py + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSexl() {
        return sexl;
    }

    public void setSexl(int sexl) {
        this.sexl = sexl;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
