package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/2/9.
 */

public class ActivityListModel implements Serializable {
    int count;
    List<ActivityModel> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ActivityModel> getResult() {
        return result;
    }

    public void setResult(List<ActivityModel> result) {
        this.result = result;
    }
}
