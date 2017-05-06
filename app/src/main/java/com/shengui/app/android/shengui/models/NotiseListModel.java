package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 */

public class NotiseListModel implements Serializable {
    int count;
    List<NoticeModel> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NoticeModel> getResult() {
        return result;
    }

    public void setResult(List<NoticeModel> result) {
        this.result = result;
    }
}
