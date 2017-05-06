package com.base.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;


/**
 * 所有的activity
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initView();
            initData();
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.getInstance().addActivity(this);
    }

    protected abstract void initView();

    protected abstract void initData();

    @SuppressWarnings("unchecked")
    protected <T extends View> T r2v(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T r2v(int resId) {
        return (T) findViewById(resId);
    }


    public String getStr(int id) {
        return getResources().getString(id);
    }

    protected  String getNullToast(String result,int toast){

        if (StringTools.isNullOrEmpty(result)){
            return getStr(toast);
        }
        return "";
    }

}
