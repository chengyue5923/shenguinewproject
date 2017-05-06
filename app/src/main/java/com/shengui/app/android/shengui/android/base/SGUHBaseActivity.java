package com.shengui.app.android.shengui.android.base;

import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/4/14.
 */

public class SGUHBaseActivity extends AppCompatActivity {
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//    ​
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);

    }
}
