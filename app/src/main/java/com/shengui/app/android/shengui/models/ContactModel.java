package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by yanbo on 16-7-14.
 */
public class ContactModel implements Serializable {
    int uid;
    String name;
    String avatar;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
