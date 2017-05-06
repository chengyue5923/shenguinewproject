package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.base.platform.utils.android.Logger;
import com.shengui.app.android.shengui.android.ui.fragment.FocusGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotSelectDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MainSelectDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.NewGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.NewSelectDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OtherDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OtherDongtaiFragment1;

import java.util.ArrayList;

/**
 * Created by User on 2016/7/21.
 */
public class MineSelectPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private MainSelectDetailFragment wealthFragment;
    private HotSelectDetailFragment rightAndPointFragment;
    private NewSelectDetailFragment rightAndPointFragmendddt;

    public MineSelectPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
        wealthFragment = new MainSelectDetailFragment();
        rightAndPointFragment =  new HotSelectDetailFragment();
        rightAndPointFragmendddt =  new NewSelectDetailFragment();
    }
    public MineSelectPagerAdapter(FragmentManager fm) {
        super(fm);
        title=new String[2];
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wealthFragment;

//                return wealthFragment == null ? new MainSelectDetailFragment() : wealthFragment;
            case 1:
                return rightAndPointFragment ;
//                return rightAndPointFragment == null ? new HotSelectDetailFragment() : rightAndPointFragment;

            case 2:
                return  rightAndPointFragmendddt;
//                return  rightAndPointFragmendddt == null ? new NewSelectDetailFragment() : rightAndPointFragmendddt;
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
        wealthFragment.refresh();
        rightAndPointFragment.refresh();
        rightAndPointFragmendddt.refresh();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
