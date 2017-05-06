package com.shengui.app.android.shengui.android.ui.utilsview;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by admin on 2016/12/23.
 */

public class InsideViewPager extends ViewPager {
    float curX = 0f;
    float downX = 0f;
    OnSingleTouchListener onSingleTouchListener;

    public InsideViewPager(Context context) {
        // TODO Auto-generated constructor stub
        super(context);
    }

    public InsideViewPager(Context context, AttributeSet attrs) {
        // TODO Auto-generated constructor stub
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        curX = ev.getX();
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = curX;
        }
        int curIndex = getCurrentItem();
        if (curIndex == 0) {
            if (downX <= curX) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (curIndex == getAdapter().getCount() - 1) {
            if (downX >= curX) {
                getParent().requestDisallowInterceptTouchEvent(false);
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.onTouchEvent(ev);
    }

    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListner(
            OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }
}
