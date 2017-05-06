package com.shengui.app.android.shengui.utils.im;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by HW on 2015/7/19.
 */
public class SquaredImageView extends ImageView {
    public SquaredImageView(Context context) {
        super(context);
    }

    public SquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
