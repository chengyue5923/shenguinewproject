package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shengui.app.android.shengui.android.ui.fragment.OtherDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OtherDongtaiFragment1;

/**
 * Created by User on 2016/7/21.
 */
public class MineInfoPagerMainAdapter extends FragmentPagerAdapter {
    private String[] title;
    private OtherDetailFragment wealthFragment;
    private OtherDongtaiFragment1 rightAndPointFragment;

    public MineInfoPagerMainAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }
    public MineInfoPagerMainAdapter(FragmentManager fm) {
        super(fm);
        title=new String[2];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wealthFragment == null ? new OtherDetailFragment() : wealthFragment;
            case 1:
                return rightAndPointFragment == null ? new OtherDongtaiFragment1() : rightAndPointFragment;

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
