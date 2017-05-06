package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.MineInfoPagerMainAdapter;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/5.
 */

public class MineOtherInfoActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.QuanZiNameText)
    TextView QuanZiNameText;
    @Bind(R.id.sexImages)
    ImageView sexImages;
    @Bind(R.id.topLauout)
    RelativeLayout topLauout;
    @Bind(R.id.addressQuanziText)
    TextView addressQuanziText;
    @Bind(R.id.titlenameLayout)
    RelativeLayout titlenameLayout;
    @Bind(R.id.allTextView)
    TextView allTextView;
    @Bind(R.id.allTextViewNumber)
    TextView allTextViewNumber;
    @Bind(R.id.aii)
    RelativeLayout aii;
    @Bind(R.id.personInfoIvItemd)
    CircleImageView personInfoIvItemd;
    @Bind(R.id.personOnelayout)
    RelativeLayout personOnelayout;
    @Bind(R.id.allLayout)
    RelativeLayout allLayout;
    @Bind(R.id.TaGoneTv)
    TextView TaGoneTv;
    @Bind(R.id.goneView)
    View goneView;
    @Bind(R.id.TaTieTv)
    TextView TaTieTv;
    @Bind(R.id.tieView)
    View tieView;
    @Bind(R.id.my_pager)
    ViewPager myPager;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    MineInfoPagerMainAdapter adapter;
    @Bind(R.id.gongqieLayout)
    RelativeLayout gongqieLayout;
    @Bind(R.id.tieLayout)
    RelativeLayout tieLayout;
    @Bind(R.id.gongNumber)
    TextView gongNumber;
    @Bind(R.id.tieNumber)
    TextView tieNumber;
    @Bind(R.id.finishLayourss)
    LinearLayout finishLayourss;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tieLayout:
                setCurrentTab(1);
                break;
            case R.id.gongqieLayout:
                setCurrentTab(0);
                break;
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        myPager.setOffscreenPageLimit(4);
        String[] strings = getResources().getStringArray(R.array.mine_info);
        myPager.setOnPageChangeListener(this);
        adapter = new MineInfoPagerMainAdapter(getSupportFragmentManager(), strings);
        myPager.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        gongqieLayout.setOnClickListener(this);
        tieLayout.setOnClickListener(this);
        finishLayourss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCurrentTab(int index) {
        ChangeView(index);
        myPager.setCurrentItem(index);
    }

    private void ChangeView(int index) {
        switch (index) {
            case 0:
                TaGoneTv.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                gongNumber.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                goneView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                TaTieTv.setTextColor(getResources().getColor(R.color.titlecolor));
                tieNumber.setTextColor(getResources().getColor(R.color.titlecolor));
                tieView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 1:
                TaTieTv.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                tieNumber.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                tieView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                TaGoneTv.setTextColor(getResources().getColor(R.color.titlecolor));
                gongNumber.setTextColor(getResources().getColor(R.color.titlecolor));
                goneView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_other_new_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

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

}
