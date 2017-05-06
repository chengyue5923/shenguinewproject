package com.shengui.app.android.shengui.android.ui.serviceui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shengui.app.android.shengui.android.ui.serviceui.SGHFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.SGUFragment;

/**
 * Created by User on 2016/7/21.
 */
public class ServiceAdapter extends FragmentPagerAdapter {
    private String[] title;
    private SGHFragment sghFragment;
    private SGUFragment sguFragment;

    public ServiceAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
        sghFragment = new SGHFragment();
        sguFragment = new SGUFragment();

    }
//    public ServiceAdapter(FragmentManager fm) {
//        super(fm);
//        title=new String[2];
//    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return sguFragment;
            case 1:
                return sghFragment ;
        }
        return null;
    }
    private int mChildCount;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public int getCount() {
        return title.length;
    }
    public void refresh (){
        sghFragment.refresh();
        sguFragment.refresh();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
