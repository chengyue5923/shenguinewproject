package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.fragment.FragmentDoctorBuyComments;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.fragment.FramentDoctorAskListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/15.
 */

public class SGHDoctorActivity extends SGUHBaseActivity {

    @Bind(R.id.doctor_tab)
    TabLayout doctorTab;
    @Bind(R.id.doctor_vp)
    ViewPager doctorVp;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private List<Fragment> fragments;
    private FramentDoctorAskListView framentDoctorAskListView;
    private FragmentDoctorBuyComments fragmentDoctorBuy;
    private FragmentDoctorBuyComments fragmentDoctorComments;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_doctor_my);
        ButterKnife.bind(this);

        tablayoutViewPageInit();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        framentDoctorAskListView.reflash();

                        swipeContainer.setRefreshing(false);//标记刷新结束
                    }
                }, 1000);
            }
        });

    }

    private void tablayoutViewPageInit() {
        fragments = new ArrayList<>();
        final String[] stringArray = getResources().getStringArray(R.array.sgh_doctor);
        framentDoctorAskListView = new FramentDoctorAskListView();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tab", stringArray[0]);
        framentDoctorAskListView.setArguments(bundle1);
        fragments.add(framentDoctorAskListView);


        fragmentDoctorBuy = new FragmentDoctorBuyComments();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tab", stringArray[1]);
        fragmentDoctorBuy.setArguments(bundle2);
        fragments.add(fragmentDoctorBuy);

        fragmentDoctorComments = new FragmentDoctorBuyComments();
        Bundle bundle3 = new Bundle();
        bundle3.putString("tab", stringArray[2]);
        fragmentDoctorComments.setArguments(bundle3);
        fragments.add(fragmentDoctorComments);


        doctorVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        doctorTab.setupWithViewPager(doctorVp);

    }
}
