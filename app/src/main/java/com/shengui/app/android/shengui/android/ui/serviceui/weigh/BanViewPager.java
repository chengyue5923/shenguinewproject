package com.shengui.app.android.shengui.android.ui.serviceui.weigh;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BanViewPager extends ViewPager {

    private boolean isCanScroll = true;

    public BanViewPager(Context context) {

        super(context);

    }

    public BanViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    public void setNoScroll(boolean noScroll) {

        this.isCanScroll = noScroll;

    }

    @Override

    public void scrollTo(int x, int y) {

        super.scrollTo(x, y);

    }

    @Override

    public boolean onTouchEvent(MotionEvent arg0) {

        if (isCanScroll) {

            return false;

        } else {

            return super.onTouchEvent(arg0);

        }

    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (isCanScroll) {

            return false;

        } else {

            return super.onInterceptTouchEvent(arg0);

        }

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view the base view with already measured height
     *
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            // set the height from the base view if available
            if (view != null) {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}