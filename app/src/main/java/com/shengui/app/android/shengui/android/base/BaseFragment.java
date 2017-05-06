package com.shengui.app.android.shengui.android.base;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/4/14.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }



    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getLocalClassName());
    }

}
