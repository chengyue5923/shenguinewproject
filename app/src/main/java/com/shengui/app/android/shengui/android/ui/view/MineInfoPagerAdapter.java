package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.fragment.AllActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.Fragment1;
import com.shengui.app.android.shengui.android.ui.fragment.HotActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MineActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OrigizionActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OtherDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OtherDetailGongActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OtherDongtaiFragment1;

/**
 * Created by User on 2016/7/21.
 */
public class MineInfoPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private OtherDetailFragment wealthFragment;
    private OtherDongtaiFragment1 rightAndPointFragment;
    private String otherid;
    private boolean isFocus=false;

    public MineInfoPagerAdapter(FragmentManager fm, String[] title,String otherid) {
        super(fm);
        this.otherid=otherid;
        this.title = title;
        if(wealthFragment==null){
            wealthFragment=new OtherDetailFragment();
        }
        wealthFragment.setOtherId(otherid);
        wealthFragment.setFocus(isFocus);
        if(rightAndPointFragment==null){
            rightAndPointFragment=new OtherDongtaiFragment1();
        }
        rightAndPointFragment.setOtherId(otherid);
        rightAndPointFragment.setFocus(isFocus);
    }
    public void adapterRefresh(){
        wealthFragment.refresh();
        rightAndPointFragment.refresh();
    }
    public MineInfoPagerAdapter(FragmentManager fm) {
        super(fm);
        title=new String[2];
    }
    public void setFocus( boolean focus){
        isFocus=focus;
        if(wealthFragment!=null){
            wealthFragment.setFocus(isFocus);
        }
        if(rightAndPointFragment!=null){
            rightAndPointFragment.setFocus(isFocus);
        }
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
//                wealthFragment.setOtherId(otherid);
//                wealthFragment.setFocus(isFocus);
                return  wealthFragment;
//                return wealthFragment == null ? new OtherDetailFragment() : wealthFragment;
            case 1:

//                rightAndPointFragment.setOtherId(otherid);
//                rightAndPointFragment.setFocus(isFocus);
                return  rightAndPointFragment;
//                return rightAndPointFragment == null ? new OtherDongtaiFragment1() : rightAndPointFragment;

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
