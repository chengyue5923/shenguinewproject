package com.shengui.app.android.shengui.android.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.anthonycr.grant.PermissionsManager;
import com.base.framwork.cach.db.factory.DBFactory;
import com.base.framwork.cach.preferce.PreferceManager;
import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.FileTools;


import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushGongQiuDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineCodeActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.sign.ResolutionUtil;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpManager;
import com.shengui.app.android.shengui.controller.IMController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.Base64Utils;
import com.shengui.app.android.shengui.utils.android.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by yanbo on 16-7-4.
 */
public class ShenGuiApplication extends BaseApplication {
    public static String cachePath="";
    private static Context mContext;
    private String fsenv;
    public static  ShenGuiApplication instance;
    List<Activity> array = new ArrayList<>();


    public void addActivityRecode(Activity activity){
        array.add(activity);
    }
    public void clearAcCach(){
        try {
            for (Activity ac:array){
                if (ac!=null&&!ac.isFinishing()){
                    Logger.e(ac+"------------------------------------------");
                    ac.finish();
                }
            }
            array.clear();

        }catch (Exception e){
            Logger.e(e.getLocalizedMessage(),e);
        }

    }
    @Override
    public Bundle getSunBundle() {
        Bundle bundle = new Bundle();
        instance = this;
        File file = new File(Constant.LOGPATH);
        if (!file.exists()) {
            file.mkdirs();

        }
        array = new ArrayList<>();
        mContext = getApplicationContext();


        //手机信息
        boolean hasPermission = PermissionsManager.getInstance().hasPermission(this, Manifest.permission.READ_PHONE_STATE);
        boolean hasPermissionss = PermissionsManager.getInstance().hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean hasPermissionswws = PermissionsManager.getInstance().hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        if (Build.VERSION.SDK_INT < 23 || hasPermission) {
            saveFsenv();
//        }
        bundle.putString("logPath", Constant.LOGPATH);
//        bundle.putBoolean("writeLog", Constant.LOG_DEBUG);
        return bundle;
    }
    public static ShenGuiApplication getInstance() {
        return instance;
    }
    public void saveFsenv() {
        try {
            fsenv = Base64Utils.encodeBASE64(getFsenv());
//            L.d("-------------fsenv----encodeBASE64--------->" + fsenv);
            Logger.e("-------------fsenv------decodeBASE64------->" + fsenv);
          UserPreference.setFenvs(fsenv);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    /** 获取手机信息*/
    public String getFsenv() throws PackageManager.NameNotFoundException {
        String resolution = ScreenUtils.getScreenWidth(mContext) + "*" + ScreenUtils.getScreenHeight(mContext); //分辨率 格式：宽*高
        String package_name = getPackageName(); //包名称
        String os = "android";//操作系统，可选:android
        String os_version = Build.VERSION.RELEASE; //操作系统版本
        String device_model = Build.MODEL; //设备型号
        String app_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName; //app版本号
        boolean hasPermission = PermissionsManager.getInstance().hasPermission(this, Manifest.permission.READ_PHONE_STATE);
        String device_id;
        if(hasPermission){
            device_id = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();//设备唯一标识 idfa
        }else{
            device_id = "";
        }
         double latitude = 0.0;
         double longitude = 0.0;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        else {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
                @Override
                public void onProviderEnabled(String provider) {

                }
                @Override
                public void onProviderDisabled(String provider) {

                }
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                latitude =0.00; //经度
                longitude = 0.00; //纬度
            }else{
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude(); //经度
                    longitude = location.getLongitude(); //纬度
                }
            }
        }
        Logger.e("location=="+ latitude + "," + longitude);
        String locations=latitude + "," + longitude;
        UserPreference.setLng(latitude);
        UserPreference.setLat(longitude);
        Log.d("wan","device_id-------"+device_id);
//        UserPreference.setCBid(device_id);
        String str = "{" + "\"resolution\":\"" + resolution + "\",\"package_name\":\"" + package_name + "\",\"os\":\"" + os + "\",\"os_version\":\"" + os_version + "\",\"device_model\":\"" + device_model + "\",\"app_version\":\"" + app_version + "\",\"location\":\"" + locations + "\",\"device_id\":\"" + device_id + "\"}";
        return str;
    }

    @Override
    public void initData() {
        ResolutionUtil.getInstance().init(this);
        //// TODO: 16-7-5  初始化一些操作
        cachePath = Environment.getExternalStorageDirectory().getPath() + File.separator + this.getCacheDir();
        FileTools.creatDir(cachePath);
        initHttpConfig();
        Logger.initDebug(true);
        String audioDir = Constant.AUDIO_PATH;
        String audioTempDir = Constant.AUDIO_TEMP_PATH;
        FileTools.creatDir(audioDir);
        FileTools.creatDir(audioTempDir);
        DBFactory factory = DBFactory.getInstance();
        factory.setContext(getApplicationContext());
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
//                PushAgent.getInstance(ShenGuiApplication.this).onAppStart();
//                PushAgent.getInstance(ShenGuiApplication.this).enable();
//                Logger.e("UmengRegistrar.getRegistrationId(context)" + UmengRegistrar.getRegistrationId(ShenGuiApplication.this));
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
//                Logger.e("onActivityResumed ===" + activity.getClass());
//                Logger.e("onActivityResumed ===" + activity.getClass().getSimpleName());
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    IMController.getInstance().oneshotSync(activity);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                Logger.e("onActivityDestroyed ===" + activity.getClass().getSimpleName());
            }
        });
        PreferceManager.getInsance().saveValueBYkey("version","0");
//        ListenNetChangeReceiver receiver = new ListenNetChangeReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(receiver,filter);


        //初始化jpushSdk

        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
//        Set<String> set = new HashSet<>();
//        set.add("andfixdemo");//名字任意，可多添加几个
//        JPushInterface.setTags(this, set, null);//设置标签


//        //数据统计初始化SDK
//        GrowingIO.startWithConfiguration(this, new Configuration()
//                .useID()
//                .trackAllFragments()
//                .setChannel("龟蜜网"));



        UserPreference.setHAVENOTICE("0");
    }

    /**
     * 实例化http的配置文件 因为放到了 values 用系统的 string 解析 并且用gson的 解析 速度暂时不考虑
     */
    private void initHttpConfig() {
        new Thread(initConfigRunnable).start();
    }

    Runnable initConfigRunnable = new Runnable() {
        @Override
        public void run() {
            HttpManager.getInstance().init();
        }
    };


}
