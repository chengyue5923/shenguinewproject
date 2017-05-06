package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/7.
 */

public class PlateModel implements Serializable {
    String type;
    String title;
    String circle_id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
