package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shengui.app.android.shengui.android.ui.fragment.Fragment1;

/**
 * Created by HongJay on 2016/8/11.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{"全部", "精华", "龟池指导", "孵化","公母鉴别"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new Fragment1();
        } else if (position == 2) {
            return new Fragment1();
        } else if(position==3){
            return new Fragment1();
        } else if(position==4){
            return new Fragment1();
        }
        return new Fragment1();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
