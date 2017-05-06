package com.shengui.app.android.shengui.android.ui.activity.activity.manage;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.view.QuanZiBasePagerAdapter;
import com.shengui.app.android.shengui.android.ui.view.QuanZiPublicBasePagerAdapter;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/27.
 */

public class SgPublicJoinQuanziDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    @Bind(R.id.backImageview)
    ImageView backImageview;
    @Bind(R.id.hotlayout)
    TextView hotlayout;
    @Bind(R.id.hotlineView)
    View hotlineView;
    @Bind(R.id.originzationLayout)
    TextView originzationLayout;
    @Bind(R.id.origiazationlineView)
    View origiazationlineView;
    @Bind(R.id.myTabLayout)
    LinearLayout myTabLayout;
    @Bind(R.id.topLayout)
    RelativeLayout topLayout;
    @Bind(R.id.my_pager)
    ViewPager myPager;
    QuanZiPublicBasePagerAdapter adapter;
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.hotlayout:
                ChangeLayoutTab(0);
              break;
            case R.id.originzationLayout:
                ChangeLayoutTab(1);
                break;
            case R.id.backImageview:
                finish();
                break;

        }
    }

    private void ChangeLayoutTab(int b) {
        if(b==0){
            myPager.setCurrentItem(0);
            hotlayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
            hotlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
            originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
            origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
        }else{
            originzationLayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
            origiazationlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
            hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
            hotlineView.setBackgroundColor(getResources().getColor(R.color.white));
            myPager.setCurrentItem(1);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        myPager.setOffscreenPageLimit(4);
        String[] strings = getResources().getStringArray(R.array.mine_base);
        myPager.setOnPageChangeListener(this);
        adapter = new QuanZiPublicBasePagerAdapter(getSupportFragmentManager(), strings);
        myPager.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        hotlayout.setOnClickListener(this);
        originzationLayout.setOnClickListener(this);
        backImageview.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_quanzi_detail_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            ChangeLayoutTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
