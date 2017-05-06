package com.shengui.app.android.shengui.android.ui.activity.activity.video;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.EditTextMultiLine;
import com.shengui.app.android.shengui.android.ui.utilsview.MultiImageSelectorActivity;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 视频认证上传界面
 * Created by Wood on 2016/4/7.
 */
public class VideoPreviewActivity extends AppCompatActivity implements View.OnClickListener ,View.OnLayoutChangeListener {
    private static final String LOG_TAG = "VideoPreviewActivity";
    private static final int RES_CODE = 111;

    /**
     * 播放进度
     */
    private static final int PLAY_PROGRESS = 110;
    private Dialog dialog;
    EditTextMultiLine titleEt;
    RelativeLayout quanzilayout;
    private VideoView videoViewShow;
    private ImageView imageViewShow;
    private Button buttonDone;
    private RelativeLayout rlBottomRoot;
    private Button buttonPlay;
    private TextView textViewCountDown,cancelTextView,pushTextView,ticic;
    private ProgressBar progressVideo;
    private TextView  addressTv;
    /**
     * 视频路径
     */
    private String path;
    /**
     * 视频时间sss
     */
    private int time;
    private int currentTime;
    private Timer timer;
    private int screenHeight = 0;
    private int keyHeight = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PLAY_PROGRESS:
//                    LogUtil.e(LOG_TAG, "getDuration：" + videoViewShow.getDuration() + "...getCurrentPosition：" + videoViewShow.getCurrentPosition());
                    time = (videoViewShow.getDuration() + 1000) / 1000;
                    currentTime = (videoViewShow.getCurrentPosition() + 1500) / 1000;
                    progressVideo.setMax(videoViewShow.getDuration());
//                    LogUtil.e(LOG_TAG, time + "..time：" + currentTime);
                    progressVideo.setProgress(videoViewShow.getCurrentPosition());
                    if (currentTime < 10) {
                        textViewCountDown.setText("00:0" + currentTime);
                    } else {
                        textViewCountDown.setText("00:" + currentTime);
                    }
                    //currentTime++;
                    //达到指定时间，停止播放
                    if (!videoViewShow.isPlaying() && time > 0) {
                        progressVideo.setProgress(videoViewShow.getDuration());
                        if (timer != null) {
                            timer.cancel();
                        }
                    }
                    break;
            }
        }
    };
    /**
     * 要上传的视频文件
     */
    private File file;
    private RelativeLayout rootLayout;
    private RelativeLayout hindlayout;
    private TextView textViewName;
    private String circletitle;
    private String circleId;
    LocationModel model=new LocationModel();
    private void assignViews() {
        addressTv=(TextView)findViewById(R.id.addressTv);
        ticic=(TextView)findViewById(R.id.ticic);
        textViewName=(TextView)findViewById(R.id.textViewName);
        rootLayout=(RelativeLayout)findViewById(R.id.rootlayout);
        hindlayout=(RelativeLayout)findViewById(R.id.hindlayout);
        hindlayout.setOnClickListener(this);
        cancelTextView=(TextView)findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(this);
        pushTextView=(TextView)findViewById(R.id.pushTextView);
        pushTextView.setOnClickListener(this);
        quanzilayout=(RelativeLayout)findViewById(R.id.quanzilayout);
        quanzilayout.setOnClickListener(this);
        titleEt=(EditTextMultiLine)findViewById(R.id.titleEt);
        videoViewShow = (VideoView) findViewById(R.id.videoView_show);
        imageViewShow = (ImageView) findViewById(R.id.imageView_show);
        buttonDone = (Button) findViewById(R.id.button_done);
        rlBottomRoot = (RelativeLayout) findViewById(R.id.rl_bottom_root);
        buttonPlay = (Button) findViewById(R.id.button_play);
        textViewCountDown = (TextView) findViewById(R.id.textView_count_down);
        progressVideo = (ProgressBar) findViewById(R.id.progressBar_loading);
        model.setId(UserPreference.getCityID());
//        addressTv.setOnClickListener(this);
        model.setName(UserPreference.getCityName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_attestation_upload);
        dialog = DialogFacory.getInstance().createRunDialog(this, "正在提交。。");
        assignViews();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight/3;
        initView();
        initData();

//        if(!UserPreference.getTOPICID().equals("")){
//            CircleId = Integer.parseInt(UserPreference.getTOPICID());
//        }
//        textViewName.setText(UserPreference.getTOPICCONTENT());
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText("视频预览");
        findViewById(R.id.title_left).setOnClickListener(this);

        buttonDone.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);

        textViewCountDown.setText("00:00");

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) videoViewShow.getLayoutParams();
        layoutParams.height = width * 4 / 3;//根据屏幕宽度设置预览控件的尺寸，为了解决预览拉伸问题
        //LogUtil.e(LOG_TAG, "mSurfaceViewWidth:" + width + "...mSurfaceViewHeight:" + layoutParams.height);
        videoViewShow.setLayoutParams(layoutParams);
        imageViewShow.setLayoutParams(layoutParams);

        FrameLayout.LayoutParams rlBottomRootLayoutParams = (FrameLayout.LayoutParams) rlBottomRoot.getLayoutParams();
        rlBottomRootLayoutParams.height = width / 3 * 2;
        rlBottomRoot.setLayoutParams(rlBottomRootLayoutParams);
    }

    public void initData() {
//        titleid=UserPreference.getTOPICID();
//        ticic.setText(UserPreference.getTOPICCONTENT());
        Intent intent = getIntent();
        addressTv.setText(UserPreference.getCityName());
        if (intent != null) {
            path="";
            path = intent.getExtras().getString("path", "");
            Logger.e("erpath------------"+path);
            file = new File(path);
            Logger.e("erpath------------"+file.exists());
        }

        if(getIntent().getSerializableExtra("topic")!=null){
            titleString=(String)getIntent().getSerializableExtra("topic");
            titleid=(String)getIntent().getSerializableExtra("topicid");
            ticic.setText(titleString);
            Logger.e("topicId"+titleString+titleid);

        }
        if(getIntent().getSerializableExtra("circletitle")!=null){
            circletitle=(String)getIntent().getSerializableExtra("circletitle");
            circleId=(String)getIntent().getSerializableExtra("circleId");
            ticic.setText(titleString);
            Logger.e("topicId"+circletitle+circleId);
            CircleId =Integer.parseInt( circleId);
            textViewName.setText(circletitle);

        }
        //获取第一帧图片，预览使用
        if (file.length() != 0) {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(path);
            Bitmap bitmap = media.getFrameAtTime();
            imageViewShow.setImageBitmap(bitmap);
            media.release();
        }
    }

    /**
     * 播放视频
     */
    private void playVideo() {

        textViewCountDown.setText("00:00");
        progressVideo.setProgress(0);

        //视频控制面板，不需要可以不设置
        //MediaController controller = new MediaController(this);
        //controller.setVisibility(View.GONE);
        //videoViewShow.setMediaController(controller);
        videoViewShow.setVideoPath(path);
        videoViewShow.start();
        videoViewShow.requestFocus();
        videoViewShow.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!videoViewShow.isPlaying()) {
                    buttonPlay.setVisibility(View.VISIBLE);
                }
            }
        });

        currentTime = 0;//时间计数器重新赋值
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(PLAY_PROGRESS);
            }
        }, 0, 100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_done:
                setResult(RES_CODE);
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                finish();
                ShenGuiApplication.getInstance().clearAcCach();
                break;
            case R.id.button_play:
                //视频播放
                buttonPlay.setVisibility(View.GONE);
                imageViewShow.setVisibility(View.GONE);
                playVideo();
                break;
            case R.id.title_left:
                finish();
                ShenGuiApplication.getInstance().clearAcCach();
                break;
            case R.id.cancelTextView:
                IntentTools.startMain(this);
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                finish();
                ShenGuiApplication.getInstance().clearAcCach();
                break;
            case R.id.pushTextView:
//                PopUpDialog();
//                PushQuestion();
                UploadFile();
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.quanzilayout:
                IntentTools.startquanzilist(this,circleCode);
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.hindlayout:
                IntentTools.startCreateTop(this,1216);
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.addressTv:
                Logger.e("df");
                IntentTools.startCityList(this,1010);
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void UploadFile(){


                String StrtitleEt=titleEt.getText().toString();
                if(StringTools.isNullOrEmpty(StrtitleEt)){

                    ToastTool.show("您还没有编辑内容");
                    return;
                }

//               if(model.getId().equals("")){
//                   ToastTool.show("您还没有选择城市");
//                   return;
//               }
            String StrtextViewName=textViewName.getText().toString();
            if(StringTools.isNullOrEmpty(StrtextViewName)){
                ToastTool.show("您还没有选择圈子");
                return;
            }

        PushController.getInstance().upLoadVideoFile(new ViewNetCallBack() {
            @Override
            public void onConnectStart() {
                if (null != dialog && !dialog.isShowing()) {
                    dialog.show();
                }
            }

            @Override
            public void onConnectEnd() {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFail(Exception e) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

                Logger.e("resultddddd"+result);
                try {
                    JSONObject jsonObject=new JSONObject(o.toString());
                    JSONObject ja=jsonObject.getJSONObject("data");
                    String Urls=ja.getString("url");
                    String url_short=ja.getString("url_short");

                    String id=ja.getString("id");
                    Logger.e("ddd"+id+Urls+url_short);
                    PushQuestion(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },  file, HttpConfig.uploadVideoFile);


    }
    private void PushQuestion(String id) {
        String strtitleEt = titleEt.getText().toString();
        if (strtitleEt.equals("")) {
            ToastTool.show("内容不能为空");
            return;
        }
        PushController.getInstance().PushTieZi(new ViewNetCallBack() {
            @Override
            public void onConnectStart() {
                if (null != dialog && !dialog.isShowing()) {
                    dialog.show();
                }
            }

            @Override
            public void onConnectEnd() {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFail(Exception e) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
                try {
                    JSONObject object = new JSONObject(o.toString());
                    Logger.e("dffdf=="+ object.toString());
                    if (!object.getBoolean("status")) {
                        ToastTool.show(object.getString("message"));
                    } else {
                        UserPreference.setISFINISHPOSR("112");
//                        PopUpDialog();
                        //IntentTools.startMain(VideoPreviewActivity.this);
                        ShenGuiApplication.getInstance().clearAcCach();
                        VideoPreviewActivity.this.finish();
                        Logger.e("dffdf"+ object.toString());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e("eee"+e.getMessage());
                }

            }
        }, CircleId, strtitleEt, "", "1", "", titleid, model.getId(),model.getName(),id);
    }
    private static final int circleCode = 10023;
    private int CircleId;
    private String titleString="";
    private String titleid="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1216){
            if(resultCode==1010){
                Log.e("mods-------------------", data.getStringExtra("topic")+data.getStringExtra("result"));
                titleString=data.getStringExtra("topic");
                titleid=data.getStringExtra("result");
                ticic.setText(titleString);
            }
        }
        if (requestCode == circleCode) {
            if (resultCode == 1010) {
                Log.e("modelsssss", data.getStringExtra("modelist"));
                CircleId = Integer.parseInt((String) data.getStringExtra("modelist"));
                textViewName.setText((String) data.getStringExtra("sname"));
            }
        }
        if (requestCode == 1010 && resultCode == 1000) {
            Logger.e("date" + data);
            if (data != null) {
                String cityiD = data.getStringExtra("variety_id");
                String city = data.getStringExtra("variety_name");
                Logger.e("daaaaaaaaaaaa" + city + cityiD);
                addressTv.setText(city);
                UserPreference.setUsualCityId(cityiD);
                UserPreference.setUsualCityName(city);
                model.setId(cityiD);
                model.setName(city);
            }
        }
    }

    //发布成功弹窗
    public void PopUpDialog() {   //弹框
        final SgActivityPushSuccessDialog PopUpDialogs = new SgActivityPushSuccessDialog(this);
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
//        PopUpDialogs.dismiss();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    PopUpDialogs.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoViewShow.stopPlayback();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rootLayout.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
            hindlayout.setVisibility(View.VISIBLE);
        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
            hindlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShenGuiApplication.getInstance().clearAcCach();
    }
}
