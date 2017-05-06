package com.base.platform.android.application;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.base.platform.utils.android.CrashHandler;
import com.base.platform.utils.android.StaticContext;
import com.base.platform.utils.java.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有的application 需要集成这个 application
 */
public abstract class BaseApplication extends Application{
    static BaseApplication instance;

    /**
     * 获取所有的application的单例
     * @return  程序的单例
     */
    public static BaseApplication getInstance() {
        return instance;
    }
    List<Activity> activitys;

    @Override
    public void onCreate() {
        super.onCreate();
        instance= this;
        activitys = new ArrayList<Activity>();
        /**
         * 实例化是否log
         */
        Bundle bundle= getSunBundle();
        if(bundle!=null){
            String logPath = bundle.getString("logPath");
            boolean writeLog =  bundle.getBoolean("writeLog");
            CrashHandler.getInstance().setPath(logPath);
            errorHandlerSwitch(writeLog);
        }
        initData();
    }
    private void errorHandlerSwitch(boolean canWriteLog) {
        if (!canWriteLog) {
            return;
        }
        CrashHandler crashHandler = CrashHandler.getInstance();
// 注册crashHandler
        crashHandler.init(getApplicationContext());
    }


    /**
     *
     * @return
     */
     public abstract Bundle getSunBundle();

    public  abstract  void initData();


    public  String getStringFromMata(String key) {
        try {
            ApplicationInfo appInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            String msg = appInfo.metaData.get(key).toString();
            return msg;
        } catch (Exception e) {
        }
        return "";
    }

    public void addActivity(Activity activity) {

        StaticContext.getInstance().setCurrentContext(activity);
        if (!activitys.contains(activity)) {
            activitys.add(activity);
        }


    }
    public void clearActivities() {
        if (!ListUtil.isNullOrEmpty(activitys)) {
            for (Activity activity : activitys) {
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }



}
