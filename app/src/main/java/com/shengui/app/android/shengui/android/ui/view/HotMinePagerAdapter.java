package com.shengui.app.android.shengui.android.ui.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shengui.app.android.shengui.android.ui.fragment.AllActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MineActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OrigizionActivityFragment;

import static android.R.attr.data;

/**
 * Created by User on 2016/7/21.
 */
public class HotMinePagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private HotActivityFragment wealthFragment;
    private OrigizionActivityFragment rightAndPointFragment;
    private AllActivityFragment rightFragment;
    private MineActivityFragment activityFragment;

    public HotMinePagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
        wealthFragment = new HotActivityFragment();
        rightAndPointFragment = new OrigizionActivityFragment();
        rightFragment = new AllActivityFragment();
        activityFragment = new MineActivityFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return wealthFragment ;

            case 1:
                return rightAndPointFragment ;

            case 2:
                return rightFragment;
            case 3:
                return activityFragment;

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


    public void  refresh (int requestCode,Intent data){

        if (requestCode == 1022){
            wealthFragment.fresh(data);
        }

        if (requestCode == 103){
            rightAndPointFragment.fresh(data);
        }
        if (requestCode == 1020){
            rightFragment.fresh(data);
        }
    }
}
