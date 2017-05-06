package com.shengui.app.android.shengui.android.ui.serviceui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public  class MyPagerAdapter extends PagerAdapter {

    List<View> views;
    public MyPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        //return viewList==null?0:viewList.size();
        return views.size();//ViewPager里的个数
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
