package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/2/12.
 */

public class FavoriteModels implements Serializable {
    int count;
    List<FavoriteModel> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<FavoriteModel> getResult() {
        return result;
    }

    public void setResult(List<FavoriteModel> result) {
        this.result = result;
    }
}
