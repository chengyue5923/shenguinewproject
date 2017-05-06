package com.shengui.app.android.shengui.android.ui.activity.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.LoadActivity;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/5.
 */

public class StartLoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.skipTv)
    TextView skipTv;
    @Bind(R.id.smart)
    TextView smart;
    @Bind(R.id.weixinImage)
    ImageView weixinImage;
    @Bind(R.id.weixinLayout)
    RelativeLayout weixinLayout;
    @Bind(R.id.qqImage)
    ImageView qqImage;
    @Bind(R.id.QQLayout)
    RelativeLayout QQLayout;
    @Bind(R.id.loginLaout)
    LinearLayout loginLaout;
    @Bind(R.id.confrmTv)
    TextView confrmTv;
    @Bind(R.id.loginTv)
    TextView loginTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.skipTv:
                IntentTools.startMain(this);
                finish();
                break;
            case R.id.loginTv:
                IntentTools.startLogin(this);
                finish();
                break;
            case R.id.confrmTv:
                IntentTools.startRegister(this);
                finish();
                break;
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        skipTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        confrmTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_login_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }

}
