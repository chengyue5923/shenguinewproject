package com.shengui.app.android.shengui.android.ui.serviceui.weigh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/11/7 0007.
 */
public class MyScorllView extends ScrollView {

    private OnScrollListener onScrollListener;

    public MyScorllView(Context context) {
        this(context,null);
    }

    public MyScorllView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScorllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置滚动接口
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
       this.onScrollListener = onScrollListener;
    }


    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener != null){
            onScrollListener.onScroll(t);
        }
    }


    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         * @param scrollY
         *              、
         */
        public void onScroll(int scrollY);
    }

}
