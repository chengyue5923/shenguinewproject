package com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.assembly.VpSwipeRefreshLayout;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentMyVideoListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MyActivity extends SGUHBaseActivity {


    @Bind(R.id.my_tab)
    TabLayout myTab;
    @Bind(R.id.my_vp)
    ViewPager myVp;
    @Bind(R.id.swipe_container)
    VpSwipeRefreshLayout swipeContainer;
    private List<Fragment> fragments;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };
    private FragmentMyVideoListView fragmentMyVideoListView0;
    private FragmentMyVideoListView fragmentMyVideoListView1;
    private FragmentMyVideoListView fragmentMyVideoListView2;
    private FragmentMyVideoListView fragmentMyVideoListView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgu_activity_my);
        ButterKnife.bind(this);
        final String[] stringArray = getResources().getStringArray(R.array.sgu_my);

        fragments = new ArrayList<>();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        for (int i = 0; i < fragments.size(); i++) {
//                            ((FragmentMyVideoListView) fragments.get(i)).reflash(stringArray);
//                        }
                        fragmentMyVideoListView0.reflash();
                        fragmentMyVideoListView1.reflash();
                        fragmentMyVideoListView2.reflash();
                        fragmentMyVideoListView3.reflash();
                        swipeContainer.setRefreshing(false);//标记刷新结束
                    }
                }, 1000);
            }
        });


        tablayoutVpInit(stringArray);
    }

    private void tablayoutVpInit(final String[] stringArray) {
        fragmentMyVideoListView0 = new FragmentMyVideoListView();
        Bundle bundle0 = new Bundle();
        bundle0.putString("tab", stringArray[0]);
        fragmentMyVideoListView0.setArguments(bundle0);
        fragments.add(fragmentMyVideoListView0);

        fragmentMyVideoListView1 = new FragmentMyVideoListView();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tab", stringArray[1]);
        fragmentMyVideoListView1.setArguments(bundle1);
        fragments.add(fragmentMyVideoListView1);

        fragmentMyVideoListView2 = new FragmentMyVideoListView();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tab", stringArray[2]);
        fragmentMyVideoListView2.setArguments(bundle2);
        fragments.add(fragmentMyVideoListView2);

        fragmentMyVideoListView3 = new FragmentMyVideoListView();
        Bundle bundle3 = new Bundle();
        bundle3.putString("tab", stringArray[3]);
        fragmentMyVideoListView3.setArguments(bundle3);
        fragments.add(fragmentMyVideoListView3);




        myVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        myTab.setupWithViewPager(myVp);
    }

    @Override
    protected void onPause() {
        super.onPause();
        swipeContainer.setRefreshing(false);
    }
}
