package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by yanbo on 2016/7/22.
 */
public class LocationModel implements Serializable {
    String id;
    String parent_id;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name_first;
    String name_py;
    String type;
    LocationBaseModel province;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocationBaseModel getProvince() {
        return province;
    }

    public void setProvince(LocationBaseModel province) {
        this.province = province;
    }
}
