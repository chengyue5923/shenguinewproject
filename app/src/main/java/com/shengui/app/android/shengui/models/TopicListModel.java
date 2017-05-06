package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/11.
 */

public class TopicListModel implements Serializable {

    List<TopicModel> result;
    int count;

    public List<TopicModel> getResult() {
        return result;
    }

    public void setResult(List<TopicModel> result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
