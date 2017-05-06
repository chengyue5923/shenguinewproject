package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shengui.app.android.shengui.android.ui.fragment.AllActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MineActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OrigizionActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.QuanziDetailItemFragment;

/**
 * Created by User on 2016/7/21.
 */
public class QuanziDetailListPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private QuanziDetailItemFragment wealthFragment;
    private QuanziDetailItemFragment rightAndPointFragment;
    private QuanziDetailItemFragment rightFragment;
    private QuanziDetailItemFragment activityFragment;
    private QuanziDetailItemFragment MaleFragment;

    public QuanziDetailListPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wealthFragment == null ? new QuanziDetailItemFragment() : wealthFragment;

            case 1:
                return rightAndPointFragment == null ? new QuanziDetailItemFragment() : rightAndPointFragment;

            case 2:
                return rightFragment == null ? new QuanziDetailItemFragment() : rightFragment;
            case 3:
                return activityFragment == null ? new QuanziDetailItemFragment() : activityFragment;
            case 4:
                return MaleFragment == null ? new QuanziDetailItemFragment() : MaleFragment;


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
