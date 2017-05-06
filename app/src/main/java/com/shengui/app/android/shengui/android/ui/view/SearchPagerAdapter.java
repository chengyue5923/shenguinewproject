package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.base.platform.utils.android.Logger;
import com.shengui.app.android.shengui.android.ui.fragment.AllActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.HotActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.MineActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.OrigizionActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.SearchGuiMiFragment;
import com.shengui.app.android.shengui.android.ui.fragment.SearchQuanziFragment;
import com.shengui.app.android.shengui.android.ui.fragment.SearchTieZiFragment;

/**
 * Created by User on 2016/7/21.
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {
    private String[] title;
    private String key;
    private SearchQuanziFragment wealthFragment;
    private SearchGuiMiFragment rightAndPointFragment;
    private SearchTieZiFragment rightFragment;

    public SearchPagerAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title = title;
    }
    public void setKey(int data,String key){
        this.key=key;
        getItem(data);
//        if(data==0){
//            wealthFragment.UpdateData(key);
//        }else if(data==1){
//            rightAndPointFragment.UpdateData(key);
//        }else{
//            rightFragment.UpdateData(key);
//        }
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Logger.e("datat"+position);
                if(wealthFragment==null){
                    wealthFragment = new SearchQuanziFragment();
                }
                wealthFragment.UpdateData(key);
                return wealthFragment;
//                return wealthFragment == null ? new SearchQuanziFragment() : wealthFragment;
            case 1:
                Logger.e("datat"+position);
                if(rightAndPointFragment==null){
                    rightAndPointFragment = new SearchGuiMiFragment();
                }
                rightAndPointFragment.UpdateData(key);
                return rightAndPointFragment;
//                return rightAndPointFragment == null ? new SearchGuiMiFragment() : rightAndPointFragment;
            case 2:
                Logger.e("datat"+position);
                if(rightFragment==null){
                    rightFragment = new SearchTieZiFragment();
                }
                rightFragment.UpdateData(key);
                return rightFragment;
//                return rightFragment == null ? new SearchTieZiFragment() : rightFragment;

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
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
