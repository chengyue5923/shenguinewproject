package com.shengui.app.android.shengui.configer.enums;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/4/14.
 */
public class NetNofityBean implements Serializable{
    int id;
    String name;

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
}
