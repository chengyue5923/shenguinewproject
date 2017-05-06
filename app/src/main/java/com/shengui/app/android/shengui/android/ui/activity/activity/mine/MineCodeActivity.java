package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.image.ImagPagerUtil;
import com.shengui.app.android.shengui.android.ui.activity.activity.video.RecordVideoGongQiuActivity;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherCodePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.configer.enums.UrlRes;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.open.utils.Util;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/3.
 */

public class MineCodeActivity extends BaseActivity implements View.OnClickListener ,ShareOtherCodePopUpDialog.OnClickListern{
    @Bind(R.id.statesText)
    ImageView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.scanText)
    TextView scanText;
    @Bind(R.id.nameTv)
    TextView nameTv;
    @Bind(R.id.maleTv)
    ImageView maleTv;
    @Bind(R.id.toplayout)
    LinearLayout toplayout;
    @Bind(R.id.addresstv)
    TextView addresstv;
    @Bind(R.id.codeIv)
    ImageView codeIv;
    @Bind(R.id.saveTv)
    TextView saveTv;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private final String W_APPID= Constant.WXIDAPP_ID;
    private IWXAPI api;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.statesText:
                ShareOtherPopUpDialog();
                break;
            case R.id.saveTv:
//                ToastTool.show("以保存");
                Logger.e("code"+UserPreference.getQCODE());
                if(UserPreference.getQCODE()!=null&&UserPreference.getQCODE().length()>1){
                    new SaveImage(UserPreference.getQCODE()).execute(); // Android 4.0以后要使用线程来访问网络
                }else{
                    ToastTool.show("您还没有登录");
                }
                break;
            case R.id.scanText:

                checkCameraPermission();
//                IntentTools.startScan(MineCodeActivity.this,1234);
                break;
        }
    }
    /**
     * 检测摄像头权限，具备相关权限才能继续
     */
    private void checkCameraPermission() {
        NO_VIDEO_PERMISSION.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < VIDEO_PERMISSION.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, VIDEO_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                    NO_VIDEO_PERMISSION.add(VIDEO_PERMISSION[i]);
                }
            }
            if (NO_VIDEO_PERMISSION.size() == 0) {
                IntentTools.startScan(MineCodeActivity.this,1234);
            } else {
                ActivityCompat.requestPermissions(this, NO_VIDEO_PERMISSION.toArray(new String[NO_VIDEO_PERMISSION.size()]), 1122);
            }
            Logger.e("sssssssssssss"+NO_VIDEO_PERMISSION);
        } else {
            IntentTools.startScan(MineCodeActivity.this,1234);
        }
    }
    //视频录制需要的权限(相机，录音，外部存储)
    private String[] VIDEO_PERMISSION = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<String> NO_VIDEO_PERMISSION = new ArrayList<String>();

    @Override
    public void Onlisten() {
        Logger.e("ads-------");
        weiChat(0);

    }
    // 0-分享给朋友  1-分享到朋友圈
    private void weiChat(int flag) {
        String url = UserPreference.getQCODE();
        if (!api.isWXAppInstalled()) {
            ToastTool.show("您还未安装微信");
            return;
        }

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl =url;
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title ="二维码分享";
        msg.description = "二维码分享";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;

        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        api.sendReq(req);
    }
    /**
     * 检测摄像头权限，具备相关权限才能继续
     */

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     *
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        String imgurl;
        public SaveImage(String url){
            imgurl=url;
        }
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                int idx = imgurl.lastIndexOf(".");
                String ext = imgurl.substring(idx);
                file = new File(sdcard + "/Download/" + new Date().getTime() +".png");
                InputStream inputStream = null;
                URL url = new URL(imgurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            new AlertDialog.Builder(MineCodeActivity.this)
                    .setTitle("提示")
                    .setMessage(result)
                    .setPositiveButton("确定",null)
                    .show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("dataee"+data);
        if(requestCode==1234&&resultCode==RESULT_OK){
            Logger.e("dataee"+(String)data.getSerializableExtra("variety_name"));
            IntentTools.startOtherDetail(this,Integer.parseInt((String)data.getSerializableExtra("variety_name")));
//            MineInfoController.getInstance().Attentionadd(this, (String)data.getSerializableExtra("variety_name"), UserPreference.getTOKEN());
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            try{
                if(UserPreference.getSex()==0){
                    maleTv.setImageResource(R.drawable.women);
                }else{
                    maleTv.setImageResource(R.drawable.male);
                }
                addresstv.setText(UserPreference.getUsualCityName());
                nameTv.setText(UserPreference.getName());
                Glide.with(this).load(UserPreference.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
            }catch (Exception e){
                Logger.e("sd"+e.getMessage());
            }
        }

        if(UserPreference.getQCODE()!=null&&UserPreference.getQCODE().length()>1){
            Glide.with(this).load(UserPreference.getQCODE()).asBitmap().placeholder(R.drawable.default_pictures).into(codeIv);
        }
    }
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, W_APPID, true);
        api.registerApp(W_APPID);
    }
    @Override
    protected void initEvent() {
        scanText.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        statesText.setOnClickListener(this);
        saveTv.setOnClickListener(this);
        statesText.setOnClickListener(this);
        regToWx();
    }

    @Override
    protected void initData() {

    }
    public void ShareOtherPopUpDialog() {   //弹框
        ShareOtherCodePopUpDialog PopUpDialogs = new ShareOtherCodePopUpDialog(this);
        PopUpDialogs.setOnListern(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_code_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.Attentionadd.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("关注成功");
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("关注失败");
            }
        }
    }

}
