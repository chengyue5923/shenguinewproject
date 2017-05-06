package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shengui.app.android.shengui.android.ui.fragment.BaseInfoActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.BaseNumberActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.PublicBaseInfoActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.PublicBaseNumberActivityFragment;

/**
 * Created by User on 2016/7/21.
 */
public class QuanZiPublicBasePagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private PublicBaseInfoActivityFragment wealthFragment;
    private PublicBaseNumberActivityFragment rightAndPointFragment;

    public QuanZiPublicBasePagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wealthFragment == null ? new PublicBaseInfoActivityFragment() : wealthFragment;
            case 1:
                return rightAndPointFragment == null ? new PublicBaseNumberActivityFragment() : rightAndPointFragment;
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
}
