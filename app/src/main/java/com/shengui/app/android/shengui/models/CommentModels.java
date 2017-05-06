package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/2/9.
 */

public class CommentModels  implements Serializable {
    int count;
    List<CommentModel> result;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentModel> getResult() {
        return result;
    }

    public void setResult(List<CommentModel> result) {
        this.result = result;
    }
}
