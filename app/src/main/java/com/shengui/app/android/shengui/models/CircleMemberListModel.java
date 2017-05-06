package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/12.
 */

public class CircleMemberListModel  implements Serializable{

    List<CircleMemberDetail> data;
    String count;

    public List<CircleMemberDetail> getData() {
        return data;
    }

    public void setData(List<CircleMemberDetail> data) {
        this.data = data;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
