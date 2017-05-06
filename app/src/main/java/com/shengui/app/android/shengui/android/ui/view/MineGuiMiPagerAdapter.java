package com.shengui.app.android.shengui.android.ui.view;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shengui.app.android.shengui.android.ui.fragment.FocusGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotSelectDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MainSelectDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.NewGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.NewSelectDetailFragment;

/**
 * Created by User on 2016/7/21.
 */
public class MineGuiMiPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private FocusGuiMiDetailFragment wealthFragment;
    private HotGuiMiDetailFragment rightAndPointFragment;
    private NewGuiMiDetailFragment rightAndPointFragmendddt;

    public MineGuiMiPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
        wealthFragment = new FocusGuiMiDetailFragment();
        rightAndPointFragment =  new HotGuiMiDetailFragment();
        rightAndPointFragmendddt =  new NewGuiMiDetailFragment();
    }
    public MineGuiMiPagerAdapter(FragmentManager fm) {
        super(fm);
        title=new String[2];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 2:
                return  wealthFragment ;
            case 1:
                return  rightAndPointFragment ;

            case 0:
                return  rightAndPointFragmendddt;
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
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }



    public void refresh (){
         wealthFragment.refresh();
         rightAndPointFragment.refresh();;
        rightAndPointFragmendddt.refresh();;
    }
}
