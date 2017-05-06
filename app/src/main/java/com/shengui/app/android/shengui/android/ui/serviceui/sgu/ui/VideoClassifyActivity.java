package com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentVideoClassify;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoClassifyActivity extends SGUHBaseActivity {


    @Bind(R.id.video_search_init)
    TextView videoSearchInit;
    @Bind(R.id.video_header_search)
    LinearLayout videoHeaderSearch;
    @Bind(R.id.video_search_back)
    LinearLayout videoSearchBack;
    @Bind(R.id.video_classify_tab)
    TabLayout videoClassifyTab;
    @Bind(R.id.video_classify_vp)
    ViewPager videoClassifyVp;
    @Bind(R.id.activity_video_classify)
    LinearLayout activityVideoClassify;
    private List<Fragment> fragments;
    private InputMethodManager methodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgu_activity_video_classify);
        ButterKnife.bind(this);



        viewOnClick();

        videoClassifyVpInit();
    }

    private void viewOnClick() {
        videoHeaderSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VideoClassifyActivity.this, SearchActivity.class));
            }
        });

        videoSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void videoClassifyVpInit() {
        final String[] stringArray = getResources().getStringArray(R.array.sgu_video_classify);

        fragments = new ArrayList<>();
        final List<String> stringList = new ArrayList<>();

        for (int i = 0; i < stringArray.length; i++) {

            FragmentVideoClassify fragmentListView = new FragmentVideoClassify();
            Bundle bundle = new Bundle();

            bundle.putString("tab", stringArray[i]);
            bundle.putInt("log", i);
            stringList.add(stringArray[i]);
            fragmentListView.setArguments(bundle);
            fragments.add(fragmentListView);

        }


        videoClassifyVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                return stringList.get(position);

            }
        });

        videoClassifyTab.setupWithViewPager(videoClassifyVp);
    }

    private void hideInput() {
        methodManager = ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow(((Activity) VideoClassifyActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
