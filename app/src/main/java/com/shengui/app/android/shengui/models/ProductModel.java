package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by yanbo on 2016/7/28.
 */
public class ProductModel implements Serializable {
    int uid;
    boolean flags=false;
    String title;

    String name;
    String gift;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isFlags() {
        return flags;
    }

    public void setFlags(boolean flags) {
        this.flags = flags;
    }
}
