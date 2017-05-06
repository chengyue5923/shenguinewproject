package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.ChildViewPager;
import com.shengui.app.android.shengui.android.ui.utilsview.InsideViewPager;
import com.shengui.app.android.shengui.android.ui.utilsview.MyViewPager;
import com.shengui.app.android.shengui.android.ui.view.QuanziDetailListPagerAdapter;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/23.
 */

public class SgGuiQuanDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    @Bind(R.id.hotlayout)
    TextView hotlayout;
    @Bind(R.id.hotlineView)
    View hotlineView;
    @Bind(R.id.originzationLayout)
    TextView originzationLayout;
    @Bind(R.id.origiazationlineView)
    View origiazationlineView;
    @Bind(R.id.allLayout)
    TextView allLayout;
    @Bind(R.id.alllineView)
    View alllineView;
    @Bind(R.id.mineLayout)
    TextView mineLayout;
    @Bind(R.id.minelineView)
    View minelineView;
    @Bind(R.id.maleTab)
    TextView maleTab;
    @Bind(R.id.maleTalineView)
    View maleTalineView;
    @Bind(R.id.viewpager)
    ViewPager myPager;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    QuanziDetailListPagerAdapter adapter;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        myPager.setOffscreenPageLimit(6);
        String[] strings = getResources().getStringArray(R.array.mine_detail);
        myPager.setOnPageChangeListener(this);
        adapter = new QuanziDetailListPagerAdapter(getSupportFragmentManager(), strings);
        myPager.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
//        backImageView.setOnClickListener(this);
        hotlayout.setOnClickListener(this);
        originzationLayout.setOnClickListener(this);
        allLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        maleTab.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gui_quan_detail;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.hotlayout:
                setCurrentTab(0);
                break;
            case R.id.originzationLayout:
                setCurrentTab(1);
                break;
            case R.id.allLayout:
                setCurrentTab(2);
                break;
            case R.id.mineLayout:
                setCurrentTab(3);
                break;
            case R.id.maleTab:
                setCurrentTab(4);
                break;
            case R.id.backImageView:
                finish();
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setCurrentTab(int index) {
        ChangeView(index);
        myPager.setCurrentItem(index);
    }

    public void ChangeView(int flags) {
        switch (flags) {
            case 0:
                hotlayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
                allLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.white));
                mineLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                minelineView.setBackgroundColor(getResources().getColor(R.color.white));
                maleTab.setTextColor(getResources().getColor(R.color.titlecolor));
                maleTalineView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 1:
                hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.white));
                originzationLayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                allLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.white));
                mineLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                minelineView.setBackgroundColor(getResources().getColor(R.color.white));
                maleTab.setTextColor(getResources().getColor(R.color.titlecolor));
                maleTalineView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 2:
                hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.white));

                originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
                allLayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                mineLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                minelineView.setBackgroundColor(getResources().getColor(R.color.white));
                maleTab.setTextColor(getResources().getColor(R.color.titlecolor));
                maleTalineView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 3:
                hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.white));
                originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
                allLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.white));
                mineLayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                minelineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                maleTab.setTextColor(getResources().getColor(R.color.titlecolor));
                maleTalineView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 4:
                hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.white));
                originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
                allLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.white));
                mineLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                minelineView.setBackgroundColor(getResources().getColor(R.color.white));
                maleTab.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                maleTalineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                break;
        }

    }


}
