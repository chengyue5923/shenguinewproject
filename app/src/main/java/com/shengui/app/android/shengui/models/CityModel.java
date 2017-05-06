package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/21.
 */

public class CityModel implements Serializable {
    String id;
    String parent_id;
    String name;
    String name_first;
    String name_py;


    //  1  g供求   2  城市    3  种类
    String typeid;

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
