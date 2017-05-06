package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/10.
 */

public class QuanZiListModel implements Serializable {


    int count;
    List<QuanziList> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<QuanziList> getResult() {
        return result;
    }

    public void setResult(List<QuanziList> result) {
        this.result = result;
    }
}
