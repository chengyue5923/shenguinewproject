package com.shengui.app.android.shengui.android.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/17.
 */

public class BaseVideoActivity  extends Activity{
    protected static final int RESULT_ERROR = 0x00000001;

    protected Intent mIntent;

    //=================  ====================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mIntent = intent;
    }
    //=================  ====================



    //================= Intent start ====================
    protected boolean hasExtra(String name) {
        if(mIntent == null) {
            return false;
        }
        return mIntent.hasExtra(name);
    }

    protected String getStringExtra(String name) {
        if(mIntent == null) {
            return null;
        }
        return mIntent.getStringExtra(name);
    }

    protected int getIntExtra(String name) {
        if(mIntent == null) {
            return -1;
        }
        return mIntent.getIntExtra(name, -1);
    }

    protected boolean getBooleanExtra(String name) {
        if(mIntent == null) {
            return false;
        }
        return mIntent.getBooleanExtra(name, false);
    }

    protected Parcelable getParcelableExtra(String name) {
        if(mIntent == null) {
            return null;
        }
        return mIntent.getParcelableExtra(name);
    }

    protected Serializable getSerializableExtra(String name) {
        if(mIntent == null) {
            return null;
        }
        return mIntent.getSerializableExtra(name);
    }
    //======== Intent end =================

    //======== Shot toast =================
    protected void showShortToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    protected void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
    //======== Shot toast end =================
}
