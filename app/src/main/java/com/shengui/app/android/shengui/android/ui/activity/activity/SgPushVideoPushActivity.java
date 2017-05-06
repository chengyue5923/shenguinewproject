package com.shengui.app.android.shengui.android.ui.activity.activity;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by admin on 2017/2/17.
 */

public class SgPushVideoPushActivity extends BaseActivity {
    @Override
    protected void initView() {


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

        if(getIntent().getSerializableExtra("path")!=null){
            String url=(String)getIntent().getSerializableExtra("path");
            Logger.e("urlllllllllllllllllll----"+url);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.empty_layout;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }
}
