package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.view.View;
import android.view.ViewGroup;

import com.shengui.app.android.shengui.android.ui.serviceui.weigh.VPReflashAdapter;


/**
 * Created by Administrator on 2017/3/13.
 */

public class SGUHomePageViewPageAdapter extends VPReflashAdapter {

    private View[] views;

    public SGUHomePageViewPageAdapter(View[] views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View adapterView = views[position % views.length];

        if (container.indexOfChild(adapterView)!=-1){
            container.removeView(adapterView);
        }

        container.addView(adapterView);

        return adapterView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
