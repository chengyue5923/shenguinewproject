package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.serviceui.ServiceFragment;
import com.shengui.app.android.shengui.android.ui.fragment.GuiMiNewFragment;
import com.shengui.app.android.shengui.android.ui.fragment.SelectedFragment;
import com.shengui.app.android.shengui.android.ui.fragment.SgMineFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.utils.im.CommonUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by yanbo on 16-7-4.
 */
public class MainTabActivity extends BaseActivity {

    @Bind(R.id.realtabcontent)
    FrameLayout realtabcontent;
    //    @Bind(R.id.drawer_layout)
//    LinearLayout drawerLayout;
    @Bind(R.id.tv_featured)
    TextView featured;
    @Bind(R.id.tv_service)
    TextView service;
    @Bind(R.id.tv_mine)
    TextView mine;
    @Bind(R.id.im_featured)
    ImageView imFeatured;
    @Bind(R.id.im_service)
    ImageView imService;
    @Bind(R.id.im_mine)
    ImageView imMine;
    @Bind(R.id.startLayout)
    RelativeLayout startLayout;
    @Bind(R.id.guimiLayout)
    RelativeLayout guimiLayout;
    @Bind(R.id.mineLayout)
    RelativeLayout mineLayout;
    @Bind(R.id.noticeIm)
    ImageView noticeIm;

    @Bind(R.id.im_find)
    ImageView imFind;
    @Bind(R.id.tv_find)
    TextView tvFind;
    @Bind(R.id.findLayout)
    RelativeLayout findLayout;

    private Fragment curFragment;
    private SelectedFragment selectedFragment;
    private GuiMiNewFragment serviceFragment;
    private SgMineFragment customerMineFragment;
    private ServiceFragment findFragment;
    private int colorBlue = 0xfffc7b42;
    private int colorGray = 0xff9f9f9f;


   static boolean b = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //6.0.0版本及以后
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserPreference.getHAVENOTICE() != null && UserPreference.getHAVENOTICE().equals("1")) {
            noticeIm.setVisibility(View.VISIBLE);
        } else {
            noticeIm.setVisibility(View.GONE);
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try {

                    JsonUitl.userStatus(MainTabActivity.this);
                    if (curFragment instanceof GuiMiNewFragment) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
                    } else if ((curFragment instanceof SelectedFragment)) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), false);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), false);
                    } else if (curFragment instanceof SgMineFragment) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
                    } else if (curFragment instanceof ServiceFragment) {
                        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
                        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
                    }
                } catch (Exception e) {
                }
            }
        }, 889);
        ShenGuiApplication.getInstance().saveFsenv();
        if (UserPreference.getLng() != null) {
            MineInfoController.getInstance().cityLocaling(this, UserPreference.getLat(), UserPreference.getLng());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
//        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10020: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ShenGuiApplication.getInstance().saveFsenv();
                } else {

                }
                return;
            }
        }
    }

    private LocationManager locationManager;
    private String locationProvider;
    //视频录制需要的权限(相机，录音，外部存储)
    private String[] VIDEO_PERMISSION = {Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<String> NO_VIDEO_PERMISSION = new ArrayList<String>();



    @Override
    protected void initView() {
        ButterKnife.bind(this);



        try {
            if (UserPreference.getHAVENOTICE() != null && UserPreference.getHAVENOTICE().equals("1")) {
                noticeIm.setVisibility(View.VISIBLE);
            } else {
                noticeIm.setVisibility(View.GONE);
            }
            NO_VIDEO_PERMISSION.clear();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (int i = 0; i < VIDEO_PERMISSION.length; i++) {
                    if (ActivityCompat.checkSelfPermission(this, VIDEO_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                        NO_VIDEO_PERMISSION.add(VIDEO_PERMISSION[i]);
                    }
                }
                if (NO_VIDEO_PERMISSION.size() != 0) {
                    ActivityCompat.requestPermissions(this, NO_VIDEO_PERMISSION.toArray(new String[NO_VIDEO_PERMISSION.size()]), 1210);
                }
            }
            ShenGuiApplication.getInstance().saveFsenv();
            MineInfoController.getInstance().BindJpush(this, UserPreference.getCBUid(), UserPreference.getTOKEN());
        } catch (Exception e) {

        }

        //获取地理位置管理器
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
//            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            showLocation(location);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);

        if ((String) getIntent().getSerializableExtra("flag") != null) {
            toCustomerMyFragment();
        } else {
            toSelectedFragment();
        }
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
//        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
//        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);

        initBar();
    }

    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
        try {
            String locationStr = "维度：" + location.getLatitude() + "\n"
                    + "经度：" + location.getLongitude();

            Logger.e("aaaa" + locationStr);
            UserPreference.setLng(location.getLatitude());
            UserPreference.setLat(location.getLongitude());

            if (UserPreference.getLng() != null) {
                MineInfoController.getInstance().cityLocaling(this, UserPreference.getLat(), UserPreference.getLng());
            }
        } catch (Exception e) {

        }

    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            showLocation(location);

        }
    };

    @Override
    protected void initEvent() {
//        mainTabSelectedLayout.setOnClickListener(layoutClickListener);
//        tabMenuServiceIv.setOnClickListener(layoutClickListener);
//        mainTabWealthLayout.setOnClickListener(layoutClickListener);

        featured.setOnClickListener(layoutClickListener);
        service.setOnClickListener(layoutClickListener);
        mine.setOnClickListener(layoutClickListener);
        tvFind.setOnClickListener(layoutClickListener);

        imFeatured.setOnClickListener(layoutClickListener);
        imService.setOnClickListener(layoutClickListener);
        imMine.setOnClickListener(layoutClickListener);
        imFind.setOnClickListener(layoutClickListener);

        startLayout.setOnClickListener(layoutClickListener);
        guimiLayout.setOnClickListener(layoutClickListener);
        mineLayout.setOnClickListener(layoutClickListener);
        findLayout.setOnClickListener(layoutClickListener);
    }

    @Override
    protected void initData() {
//        UserController.getInstance().userInfo(this);
//        LoginController.getInstance().bindDevice(MainTabActivity.this, UmengRegistrar.getRegistrationId(MainTabActivity.this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    View.OnClickListener layoutClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.im_featured:
                    Log.e("test", "im_featured: "+"12313123123123123123");
                    toSelectedFragment();
                    break;
                case R.id.im_service:
                    Log.e("test", "im_service: "+"12313123123123123123");
                    toServiceFragment();
                    break;
                case R.id.im_mine:
                    Log.e("test", "im_mine: "+"12313123123123123123");
                    toCustomerMyFragment();
                    break;
                case R.id.startLayout:
                    Log.e("test", "startLayout: "+"12313123123123123123");
                    toSelectedFragment();
                    break;
                case R.id.guimiLayout:
                    Log.e("test", "guimiLayout: "+"12313123123123123123");
                    toServiceFragment();
                    break;
                case R.id.mineLayout:
                    Log.e("test", "mineLayout: "+"12313123123123123123");
                    toCustomerMyFragment();
                    break;
                case R.id.im_find:
                    Log.e("test", "onClick: "+"12313123123123123123");
                    toFindFragment();

                    break;
            }
        }
    };


    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//    }

    private void toServiceFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
        clearAllMenu();
//        service.setSelected(true);
        setSelected(1);
        serviceFragment = (GuiMiNewFragment) getSupportFragmentManager().findFragmentByTag(GuiMiNewFragment.class.getName());
        if (null == serviceFragment) {
            serviceFragment = GuiMiNewFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, serviceFragment, GuiMiNewFragment.class.getName());
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    private void toCustomerMyFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
        clearAllMenu();

//        mine.setSelected(true);
        setSelected(2);
        customerMineFragment = (SgMineFragment) getSupportFragmentManager().findFragmentByTag(SgMineFragment.class.getName());
        if (null == customerMineFragment) {
            customerMineFragment = SgMineFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, customerMineFragment, SgMineFragment.class.getName());
    }

    private void toFindFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), true);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), true);
        clearAllMenu();

//        mine.setSelected(true);
        setSelected(3);
        findFragment = (ServiceFragment) getSupportFragmentManager().findFragmentByTag(ServiceFragment.class.getName());
        if (null == findFragment) {
            findFragment = ServiceFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, findFragment, ServiceFragment.class.getName());
    }

    private void toSelectedFragment() {
        CommonUtil.FlymeSetStatusBarLightMode(getWindow(), false);
        CommonUtil.MIUISetStatusBarLightMode(getWindow(), false);
        clearAllMenu();
//        featured.setSelected(true);
//        setSelected(featured, service, mine);
        setSelected(0);
        selectedFragment = (SelectedFragment) getSupportFragmentManager().findFragmentByTag(SelectedFragment.class.getName());
        if (null == selectedFragment) {
            selectedFragment = SelectedFragment.newInstance();
        }
        switchContent(R.id.realtabcontent, curFragment, selectedFragment, SelectedFragment.class.getName());
    }


    public void switchContent(int id, Fragment from, Fragment to, String tag) {
        if (from != to) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                if (null == from) {
                    transaction.add(id, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).add(id, to, tag).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                }
            } else {
                if (null == from) {
                    transaction.show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                } else {
                    transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            curFragment = to;
        }
    }

    private void setSelected(TextView tv1, TextView tv2, TextView tv3) {
        tv1.setTextColor(colorBlue);
        tv2.setTextColor(colorGray);
        tv3.setTextColor(colorGray);
    }

    private void setSelected(int flags) {
        switch (flags) {
            case 0:
                imFeatured.setBackgroundResource(R.drawable.image_first_image);
                imService.setBackgroundResource(R.drawable.guimi_unselect_img);
                imMine.setBackgroundResource(R.drawable.mine_unselect_image);
                imFind.setBackgroundResource(R.mipmap.icon_service_normal);
                break;
            case 1:
                imFeatured.setBackgroundResource(R.drawable.first_unselect_image);
                imService.setBackgroundResource(R.drawable.guimi_select_iamge);
                imMine.setBackgroundResource(R.drawable.mine_unselect_image);
                imFind.setBackgroundResource(R.mipmap.icon_service_normal);
                break;
            case 2:
                imFeatured.setBackgroundResource(R.drawable.first_unselect_image);
                imService.setBackgroundResource(R.drawable.guimi_unselect_img);
                imMine.setBackgroundResource(R.drawable.mine_select_image);
                imFind.setBackgroundResource(R.mipmap.icon_service_normal);
                break;
            case 3:
                imFeatured.setBackgroundResource(R.drawable.first_unselect_image);
                imService.setBackgroundResource(R.drawable.guimi_unselect_img);
                imMine.setBackgroundResource(R.drawable.mine_unselect_image);
                imFind.setBackgroundResource(R.mipmap.icon_service_active);
                break;
        }
    }

    private void setUnSelected(TextView tv1, TextView tv2, TextView tv3) {
        tv1.setTextColor(colorGray);
        tv2.setTextColor(colorGray);
        tv3.setTextColor(colorGray);
        imFeatured.setBackgroundResource(R.drawable.start_unselect_img);
        imService.setBackgroundResource(R.drawable.guimi_unselect_img);
        imMine.setBackgroundResource(R.drawable.ming_unselect_img);
        imFind.setBackgroundResource(R.mipmap.icon_service_normal);
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (null != curFragment) {
            outState.putString("tag", curFragment.getTag());
            getSupportFragmentManager().putFragment(outState, curFragment.getTag(), curFragment);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String tag = savedInstanceState.getString("tag");
        Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, tag);
        curFragment = fragment;
        if (UserPreference.getHAVENOTICE() != null && UserPreference.getHAVENOTICE().equals("1")) {
            noticeIm.setVisibility(View.VISIBLE);
        } else {
            noticeIm.setVisibility(View.GONE);
        }
        if (SelectedFragment.class.getName().equalsIgnoreCase(tag)) {
            toSelectedFragment();
        } else if (GuiMiNewFragment.class.getName().equalsIgnoreCase(tag)) {
            toServiceFragment();
        } else if (SgMineFragment.class.getName().equalsIgnoreCase(tag)) {
            toCustomerMyFragment();
        }else if (ServiceFragment.class.getName().equalsIgnoreCase(tag)){
            toFindFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment f : getSupportFragmentManager().getFragments()) {
            if (null == f) {
                continue;
            }
            if (!f.getTag().equalsIgnoreCase(tag)) {
                ft.hide(f);
            }
            /*else {
                ft.show(f);
			}*/
        }
        ft.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IMStringEvent event) {
        Logger.e("currwent----------------------------" + event.getNotice());
        noticeIm.setVisibility(View.VISIBLE);
    }

    /****
     * 清除按钮的选中状态
     */
    private void clearAllMenu() {
//        tabMenuSelectedTextCN.setSelected(false);
//        tabMenuServiceIv.setSelected(false);
//        tabMenuWealthTextCN.setSelected(false);
//        tabMenuSelectedEN.setSelected(false);
//        tabMenuWealthTextEN.setSelected(false);

        service.setSelected(false);
        featured.setSelected(false);
        mine.setSelected(false);


        setUnSelected(featured, service, mine);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (EventManager.getInstance().isRegister(this)) {
//            EventManager.getInstance().unregister(this);
//        }
//        ButterKnife.unbind(this);
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
//        if (flag == HttpConfig.userInfo.getType()) {
//            UserModel model = (UserModel) result;
//            UserPreference.setUser(model);
//            EventBus.getDefault().post(new UpdateAvatarEvent());
//        }
        if (flag == HttpConfig.CityLocaling.getType()) {
            Logger.e("ssss" + o.toString());

            try {
                LocationModel model = (LocationModel) result;
                UserPreference.SetLocationIng(model);

            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (JCVideoPlayer.backPress()) {
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitApp();
        }
        return false;
    }

    /****
     * 再按一次退出程序
     */
    private long exitTime = 0;

    public void ExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出龟蜜", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }
}
