package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/10.
 */

public class TieZiDetailListModel implements Serializable {


    int count;
    List<TieZiDetailModel> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TieZiDetailModel> getResult() {
        return result;
    }

    public void setResult(List<TieZiDetailModel> result) {
        this.result = result;
    }
}
