package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.utilsview.ImagePagerView;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2015/12/16.
 */
public class LoadActivity extends BaseActivity implements ViewNetCallBack ,ImagePagerView.surrentItemListener{
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
    public static final int REQUEST_APP_SETTINGS = 101;
    private static final int REQUEST_CODE = 0;//请求码
    ImagePagerView pager;
//    ImageView Image;
    TextView skipTv;
    List<String> paths;
    private static final int PLAY_PROGRESS = 1101;
    private Timer timer;

    private Button btn_login;
    private   boolean isNoClick=true;

    int count=4;
    private boolean isFirst=false;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        View view = View.inflate(this, R.layout.ac_load_activity, null);
        pager=(ImagePagerView)view.findViewById(R.id.pager);
        skipTv=(TextView)view.findViewById(R.id.skipTv);
        btn_login=(Button)view.findViewById(R.id.btn_login);
        // 读取SharedPreferences中需要的数据
        preferences = getSharedPreferences("count",MODE_WORLD_READABLE);
        int count = preferences.getInt("count", 0);
        //判断程序与第几次运行，如果是第一次运行则跳转到引导页面
        Logger.e("count"+count);
        if (count==0) {
            isFirst=true;
            MineInfoController.getInstance().get_install_boot_map(this);
//            IntentTools.startOrderPage(this);
        }else{
            skipTv.setVisibility(View.VISIBLE);
            isFirst=false;
            MineInfoController.getInstance().get_ad_page(this);
        }
        editor = preferences.edit();
        //存入数据
        editor.putInt("count", ++count);
        //提交修改
        editor.commit();


        skipTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.stopService();
                isNoClick=false;
                IntentTools.startMain(LoadActivity.this);
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.stopService();
                isNoClick=false;
                IntentTools.startMain(LoadActivity.this);
                finish();
            }
        });
        pager.setOnCurrentLister(this);
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isNoClick=false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
//                        selectedRefresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
//                        selectedRefresh.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        pager.setOnItemClickLisener(new ImagePagerView.OnItemClickLisener() {
            @Override
            public void onItemClick(View view, int position) {
//                Logger.e("posito" + position);
                if(!isFirst){
                    if(!StringTools.isNullOrEmpty(modeler.get(position).getRedirect_url())){
                        isNoClick=false;
                        if(modeler!=null){
                            IntentTools.startMain(LoadActivity.this);
                            if(modeler!=null&&modeler.size()>0){
                                SkipActivity(modeler.get(position));
                            }
                            finish();
                        }
                    }
                }

            }
        });
        setContentView(view);
    }
    private int getCount() {
            count--;
        if(count<1){
            count=1;
        }
            return count;
    }
    private Handler timehandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0&&!isFinishing()) {
                skipTv.setText(getCount()+" s");
                timehandler.sendEmptyMessageDelayed(0, 1000);
            }
        };
    };
    @Override
    public void OnFinishListener(int count) {
        if(isFirst){
            if(count==modelsd.size()-1){
                skipTv.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
            }else{
                skipTv.setVisibility(View.GONE);
                btn_login.setVisibility(View.GONE);
            }
        }else{
            if(isNoClick){
                IntentTools.startMain(LoadActivity.this);
                finish();
            }
        }
    }

    @Override
    public void FirstAndFinish() {
        IntentTools.startMain(LoadActivity.this);
        finish();
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onFail(Exception e) {
        Logger.e("eeeee"+e.getMessage());
        super.onFail(e);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_load;
    }
    List<ImageModel> modeler;
    List<ImageModel> modelsd;

//    public void interMain(){
//        Logger.e("sss-------isNoClick--------"+isNoClick);
//        if(isNoClick){
//            Logger.e("sss---------------");
//                new Thread(new Runnable() {
//                    public void run() {
//                        try {
//                            Thread.sleep(paths.size()*2000);
//                        } catch (InterruptedException e) {
//                        }
//                        IntentTools.startMain(LoadActivity.this);
//                        finish();
//                    }
//                }).start();
//        }
//    }
    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        try {
            if(flag== HttpConfig.get_ad_page.getType()){
                skipTv.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                Logger.e("data"+o.toString());
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    modeler =(List<ImageModel>)result;

                    paths = new ArrayList<>();
                    for(int i=0;i<modeler.size();i++){
                        Logger.e("sss"+modeler.get(i).getImg());
                        paths.add(modeler.get(i).getImg());
                    }
                    Logger.e("sss------paths---------");
                    pager.initData(paths);
                    timehandler.sendEmptyMessageDelayed(0, 1000);
                }

                Logger.e("sss---------------");
            } else{
                skipTv.setVisibility(View.GONE);
                btn_login.setVisibility(View.GONE);
                Logger.e("data"+o.toString());
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {

                    JSONArray ja=object.getJSONArray("data");

                    modelsd = GsonTool.jsonToArrayEntity(ja.toString(),ImageModel.class);
                    paths = new ArrayList<>();
                    for(int i=0;i<modelsd.size();i++){
                        Logger.e("sss"+modelsd.get(i).getImg_1());
                        paths.add(modelsd.get(i).getImg_1());
                    }
                    pager.initData(paths,false);
                } else {
                    ToastTool.show(object.getString("message"));
                    IntentTools.startMain(LoadActivity.this);
                    finish();
                }
            }

            if (paths.size()==1){
                handler.sendEmptyMessageDelayed(0,4000);
            }
        } catch (Exception e) {
            ToastTool.show("登录失败");
        }
    }
    Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            pager.stopService();
            isNoClick=false;
            if (isFinishing()){
                return;
            }
            IntentTools.startMain(LoadActivity.this);
            finish();
        }
    };
    //首页轮播图和四张菱形图的跳转
    public void SkipActivity(ImageModel model) {
        if (model != null) {
            switch (model.getRedirect_type()) {
                case "0":
                    if(!StringTools.isNullOrEmpty(model.getRedirect_url())){
                        IntentTools.startWebViewActivity(this, model.getRedirect_url(), model.getName());
                    }
                    break;
                case "1":
                    IntentTools.startTieZiDetail(this, model.getRedirect_url());
                    break;
                case "2":
                    IntentTools.startGongQiuDetail(this, model.getRedirect_url());
                    break;
                case "3":
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()), UserPreference.getUid());
//                        IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentQuanziDetail(this, Integer.parseInt(model.getRedirect_url()));
                    }
                    break;
                case "4":
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()), UserPreference.getUid());
//                    IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentQuanziDetail(this, Integer.parseInt(model.getRedirect_url()));
                    }
                    break;
                case "5":
                    IntentTools.startTopicList(this);
                    break;
                case "6":
                    IntentTools.startTopicDetail(this, model.getRedirect_url());
                    break;
                case "7":
                    IntentTools.startDetail(this, model.getRedirect_url());
                    break;
                case "8":
                    if(!StringTools.isNullOrEmpty(model.getRedirect_url())){
                        IntentTools.startTextView(this, model.getRedirect_url());
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 应用设置页
     */
    private void goToAppSettings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
//        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, REQUEST_APP_SETTINGS);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i("wan", "Activity-onRequestPermissionsResult() PermissionsManager.notifyPermissionsChange()");
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_APP_SETTINGS) {
            if (PermissionsManager.getInstance().hasPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                ShenGuiApplication.getInstance().saveFsenv();
                IntentTools.startMain(LoadActivity.this);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
