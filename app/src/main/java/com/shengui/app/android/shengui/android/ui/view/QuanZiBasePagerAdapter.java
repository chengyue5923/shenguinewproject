package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shengui.app.android.shengui.android.ui.fragment.AllActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.BaseInfoActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.BaseNumberActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MineActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OrigizionActivityFragment;
import com.shengui.app.android.shengui.models.QuanziList;

/**
 * Created by User on 2016/7/21.
 */
public class QuanZiBasePagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private BaseInfoActivityFragment wealthFragment;
    private BaseNumberActivityFragment rightAndPointFragment;

    private QuanziList model;
    public QuanZiBasePagerAdapter(FragmentManager fm, String[] title,QuanziList mo) {
        super(fm);
        model=mo;
        this.title = title;
        wealthFragment =  new BaseInfoActivityFragment().newInstance(model);
        rightAndPointFragment =  new BaseNumberActivityFragment().newInstance(model);
    }

    String path="";
    public void setPath(String pa){
        path=pa;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wealthFragment ;
            case 1:
                return rightAndPointFragment ;
        }
        return null;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }



    public void refreshImage(String path){
        wealthFragment.refreshImage(path);
    }


}
