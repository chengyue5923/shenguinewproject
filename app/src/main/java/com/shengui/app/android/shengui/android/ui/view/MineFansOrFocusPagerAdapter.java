package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shengui.app.android.shengui.android.ui.fragment.BaseInfoActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.BaseNumberActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MineFansNumberActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MineGuiMiNumberActivityFragment;

/**
 * Created by User on 2016/7/21.
 */
public class MineFansOrFocusPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private MineGuiMiNumberActivityFragment wealthFragment;
    private MineFansNumberActivityFragment rightAndPointFragment;

    public MineFansOrFocusPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wealthFragment == null ? new MineGuiMiNumberActivityFragment() : wealthFragment;
            case 1:
                return rightAndPointFragment == null ? new MineFansNumberActivityFragment() : rightAndPointFragment;
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
