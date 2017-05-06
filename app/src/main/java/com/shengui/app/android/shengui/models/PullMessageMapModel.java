package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/6.
 */

public class PullMessageMapModel implements Serializable {
    List<MessageModel> data=new ArrayList<>();


    public List<MessageModel> getModel() {
        return data;
    }

    public void setModel(List<MessageModel> model) {
        this.data = model;
    }
}
