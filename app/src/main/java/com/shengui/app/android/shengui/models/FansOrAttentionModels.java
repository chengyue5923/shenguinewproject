package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/2/12.
 */

public class FansOrAttentionModels implements Serializable{
    int count;
    List<FansOrAttentionModel> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<FansOrAttentionModel> getResult() {
        return result;
    }

    public void setResult(List<FansOrAttentionModel> result) {
        this.result = result;
    }
}
