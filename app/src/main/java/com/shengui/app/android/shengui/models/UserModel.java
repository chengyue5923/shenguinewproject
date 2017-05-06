package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanbo on 2016/7/28.
 */
public class UserModel implements Serializable {
    int uid;
    String realname;
    String gender;
    String avatar="";
    String level="";
    String loginRole="";
    String mobile="";
    String login="";
    String passportCode="";
    String department="";
    String position="";
    String cb_id;


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getCb_id() {
        return cb_id;
    }

    public void setCb_id(String cb_id) {
        this.cb_id = cb_id;
    }


    public String getPassportCode() {
        return passportCode;
    }

    public void setPassportCode(String passportCode) {
        this.passportCode = passportCode;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLoginRole() {
        return loginRole;
    }

    public void setLoginRole(String loginRole) {
        this.loginRole = loginRole;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "uid=" + uid +
                ", realname='" + realname + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", level='" + level + '\'' +
                ", loginRole='" + loginRole + '\'' +
                ", mobile='" + mobile + '\'' +
                ", login='" + login + '\'' +
                ", passportCode='" + passportCode + '\'' +
                ", cb_id='" + cb_id + '\'' +
                '}';
    }
}
