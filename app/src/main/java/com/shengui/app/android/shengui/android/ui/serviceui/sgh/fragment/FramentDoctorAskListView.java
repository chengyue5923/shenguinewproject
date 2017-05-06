package com.shengui.app.android.shengui.android.ui.serviceui.sgh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/15.
 */

public class FramentDoctorAskListView extends BaseFragment {

    View view;
    @Bind(R.id.doctor_ask_tab)
    TabLayout doctorAskTab;
    @Bind(R.id.doctor_ask_vp)
    ViewPager doctorAskVp;
    private List<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.sgh_fragment_doctor_ask, container, false);
        ButterKnife.bind(this, view);

        tablayoutViewPageInit();
        return view;
    }

    public void reflash(){
        if (fragments.size()!=0){
            final String[] stringArray = getResources().getStringArray(R.array.sgh_doctor_ask);
            for (int i = 0; i < fragments.size(); i++) {
                ((FragmentDoctorAskItemList) fragments.get(i)).reflash(stringArray);
            }
        }
    }

    private void tablayoutViewPageInit() {
        fragments = new ArrayList<>();
        final String[] stringArray = getResources().getStringArray(R.array.sgh_doctor_ask);

        for (int i = 0; i < stringArray.length; i++) {
            FragmentDoctorAskItemList fragmentSGHHomePage = new FragmentDoctorAskItemList();
            Bundle bundle = new Bundle();
            bundle.putString("tab", stringArray[i]);
            fragmentSGHHomePage.setArguments(bundle);
            fragments.add(fragmentSGHHomePage);
        }

        doctorAskVp.setOffscreenPageLimit(5);

        doctorAskVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return stringArray[position];
            }
        });

        doctorAskTab.setupWithViewPager(doctorAskVp);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
