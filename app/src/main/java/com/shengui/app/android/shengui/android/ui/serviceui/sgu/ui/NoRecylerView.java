package com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/3/29.
 */

public class NoRecylerView extends RecyclerView {
    public NoRecylerView(Context context) {
        super(context);
    }

    public NoRecylerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
