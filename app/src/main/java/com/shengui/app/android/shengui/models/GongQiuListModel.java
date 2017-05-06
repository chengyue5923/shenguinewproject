package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/10.
 */

public class GongQiuListModel implements Serializable {
    int count;
    List<GongQiuDetailModel> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<GongQiuDetailModel> getResult() {
        return result;
    }

    public void setResult(List<GongQiuDetailModel> result) {
        this.result = result;
    }
}
