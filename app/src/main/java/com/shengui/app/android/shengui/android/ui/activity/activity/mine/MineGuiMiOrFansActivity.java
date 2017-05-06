package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.view.MineFansOrFocusPagerAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.FansOrAttentionModels;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/5.
 */

public class MineGuiMiOrFansActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @Bind(R.id.titleName)
    TextView titleName;
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.TaGoneTv)
    TextView TaGoneTv;
    @Bind(R.id.gongNumber)
    TextView gongNumber;
    @Bind(R.id.gongqieLayout)
    RelativeLayout gongqieLayout;
    @Bind(R.id.goneView)
    View goneView;
    @Bind(R.id.TaTieTv)
    TextView TaTieTv;
    @Bind(R.id.tieNumber)
    TextView tieNumber;
    @Bind(R.id.tieView)
    View tieView;
    @Bind(R.id.tieLayout)
    RelativeLayout tieLayout;
    @Bind(R.id.my_pager)
    ViewPager myPager;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    MineFansOrFocusPagerAdapter adapter;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gongqieLayout:
                setCurrentTab(0);
                break;
            case R.id.tieLayout:
                setCurrentTab(1);
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
        String[] strings = getResources().getStringArray(R.array.mine_guimi);
        myPager.setOnPageChangeListener(this);
        adapter = new MineFansOrFocusPagerAdapter(getSupportFragmentManager(), strings);
        myPager.setAdapter(adapter);
        int flags;
        if (getIntent().getSerializableExtra("flags") != null) {
            flags = (int) getIntent().getSerializableExtra("flags");
        } else {
            flags = 0;
        }
        setCurrentTab(flags);
    }

    @Override
    protected void initEvent() {
        gongqieLayout.setOnClickListener(this);
        tieLayout.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
//        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
//
//            MineInfoController.getInstance().get_attension_my(this,0,10,UserPreference.getTOKEN());
//            MineInfoController.getInstance().get_my_attension(this,0,10,UserPreference.getTOKEN());
//        }else {
//
//            ToastTool.show("您还没有登录");
//        }
    }

    private void setCurrentTab(int index) {
        ChangeView(index);
        myPager.setCurrentItem(index);
    }

    private void ChangeView(int index) {
        switch (index) {
            case 0:
                titleName.setText("关注列表");
                TaGoneTv.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                goneView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                TaTieTv.setTextColor(getResources().getColor(R.color.titlecolor));
                tieView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 1:
                titleName.setText("粉丝列表");
                TaTieTv.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                tieView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                TaGoneTv.setTextColor(getResources().getColor(R.color.titlecolor));
                goneView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_guimi_fans_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
//
//        if(flag== HttpConfig.get_attension_my.getType()){
//            Logger.e("loggget_attension_my"+result.toString());
//            FansOrAttentionModels models=(FansOrAttentionModels)result;
//        }else if(flag==HttpConfig.get_my_attension.getType()){
//            Logger.e("logget_my_attension"+result.toString());
//            FansOrAttentionModels models=(FansOrAttentionModels)result;
//        }
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
