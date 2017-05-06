package com.shengui.app.android.shengui.android.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.base.framwork.cach.preferce.PreferceManager;
import com.base.framwork.net.configer.Constans;
import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.utils.android.SystemBarTintManager;
import com.shengui.app.android.shengui.utils.im.LogoutEvent;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.kdmobi.gui.R.id.dialog;


/**
 * Activity基类
 * 所有Activity的基类
 * Created by yanbo on 16-7-4.
 */
public abstract class BaseActivity extends AppCompatActivity implements ViewNetCallBack {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        try {
            initView();
            initData();
            initEvent();

        } catch (Exception e) {

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        BaseApplication.getInstance().addActivity(this);
    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();

    protected abstract int getLayoutId();

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    /**
     * 实例化沉浸式的bar
     */
    protected void initBarActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatuss(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.appColorBlue);//通知栏所需颜色
    }

    public void initBarActivity(boolean falg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatuss(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        if (falg) {
            tintManager.setStatusBarTintResource(R.color.appColorBlue);//通知栏所需颜色
        } else {
            tintManager.setStatusBarTintResource(R.color.transparence);//通知栏所需颜色
        }
    }

    //    @TargetApi(19)
    private void setTranslucentStatuss(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (EventManager.getInstance().isRegister(this))
//            EventManager.getInstance().unregister(this);
    }

    //    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(LogoutEvent event) {
//        if (event!=null){
//            Logger.e("onEventMainThread=====logout==");
//        }
//    }
    @SuppressWarnings("unchecked")
    protected <T extends View> T r2v(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T r2v(int resId) {
        return (T) findViewById(resId);
    }


    @Override
    public void onConnectStart() {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onConnectEnd() {

    }

    /**
     * 实例化沉浸式的bar
     */
    protected void initBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(this, true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setTintResource(R.color.transparence);
//        }
    }

    public static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
//            winParams.flags |= WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
//            winParams.flags |= WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }

    protected void logout() {
//        new BaseDao().dropAllTable();

//        IntentTools.startLogin(this);
//        PreferceManager.getInsance().saveValueBYkey("version", "");
//        ShenGuiApplication.getInstance().clearActivities();
        finish();
    }

}
