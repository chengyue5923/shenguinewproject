package com.shengui.app.android.shengui;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }

}
