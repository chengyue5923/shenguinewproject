package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/9.
 */

public class TagModel implements Serializable {
    String id;
    String title;
    String circle_id;
    String name;
    String is_open;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }
}
