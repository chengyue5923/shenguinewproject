package com.shengui.app.android.shengui.android.ui.activity.activity.video;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.EditTextMultiLine;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 视频认证上传界面
 * Created by Wood on 2016/4/7.
 */
public class VideoPreviewGongQiuActivity extends AppCompatActivity implements View.OnClickListener, View.OnLayoutChangeListener {
    private static final String LOG_TAG = "VideoPreviewActivity";
    private static final int RES_CODE = 111;

    /**
     * 播放进度
     */
    private static final int PLAY_PROGRESS = 110;

    EditTextMultiLine titleEt;
    @Bind(R.id.title_left_tv)
    TextView titleLeftTv;
    @Bind(R.id.title_left)
    LinearLayout titleLeft;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.view_title_bottom_line)
    View viewTitleBottomLine;
    @Bind(R.id.rl_title_root)
    RelativeLayout rlTitleRoot;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.typeSelectLayout)
    RelativeLayout typeSelectLayout;
    @Bind(R.id.sellTextView)
    TextView sellTextView;
    @Bind(R.id.BuyTextView)
    TextView BuyTextView;
    @Bind(R.id.titleTextVisew)
    EditText titleTextVisew;
    @Bind(R.id.faceimage)
    ImageView faceimage;
    @Bind(R.id.progressBar_loading)
    ProgressBar progressBarLoading;
    @Bind(R.id.rootlayout)
    RelativeLayout rootlayout;
    @Bind(R.id.addressTv)
    TextView addressTv;
    @Bind(R.id.selectImageTv)
    TextView selectImageTv;
    private VideoView videoViewShow;
    private ImageView imageViewShow;
    private Button buttonDone;
    private RelativeLayout rlBottomRoot;
    private Button buttonPlay;
    private TextView textViewCountDown, cancelTextView, pushTextView, ticic;
    private ProgressBar progressVideo;
    /**
     * 视频路径
     */
    private String path;
    /**
     * 视频时间
     */
    LocationModel model=new LocationModel();
    private Dialog dialog;
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
    private int Selletype = 1;   //供求
    private File file;
    private RelativeLayout rootLayout;
    private RelativeLayout hindlayout;
    private final static int typeSelect = 1001;
    private void assignViews() {
        ticic = (TextView) findViewById(R.id.ticic);
        rootLayout = (RelativeLayout) findViewById(R.id.rootlayout);
        hindlayout = (RelativeLayout) findViewById(R.id.hindlayout);
        hindlayout.setOnClickListener(this);
        cancelTextView = (TextView) findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(this);
        pushTextView = (TextView) findViewById(R.id.pushTextView);
        pushTextView.setOnClickListener(this);
        titleEt = (EditTextMultiLine) findViewById(R.id.titleEt);
        videoViewShow = (VideoView) findViewById(R.id.videoView_show);
        imageViewShow = (ImageView) findViewById(R.id.imageView_show);
        buttonDone = (Button) findViewById(R.id.button_done);
        rlBottomRoot = (RelativeLayout) findViewById(R.id.rl_bottom_root);
        buttonPlay = (Button) findViewById(R.id.button_play);
        textViewCountDown = (TextView) findViewById(R.id.textView_count_down);
        progressVideo = (ProgressBar) findViewById(R.id.progressBar_loading);
        typeSelectLayout.setOnClickListener(this);
        sellTextView.setOnClickListener(this);
        BuyTextView.setOnClickListener(this);
//        addressTv.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_attestation_upload_gongqiue);
        dialog = DialogFacory.getInstance().createRunDialog(this, "正在提交。。");
        ButterKnife.bind(this);
        assignViews();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        initView();
        initData();
        hindlayout.setVisibility(View.GONE);
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText("视频预览");
        findViewById(R.id.title_left).setOnClickListener(this);
        model.setId(UserPreference.getCityID());
        model.setName(UserPreference.getCityName());
        addressTv.setText(UserPreference.getCityName());
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
        Intent intent = getIntent();
        if (intent != null) {
            path = intent.getExtras().getString("path", "");
            file = new File(path);
        }

        //获取第一帧图片，预览使用
        if (file.length() != 0) {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(path);
            Bitmap bitmap = media.getFrameAtTime();
            imageViewShow.setImageBitmap(bitmap);
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
                finish();
                break;
            case R.id.button_play:
                if(videoViewShow!=null&&videoViewShow.isPlaying()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }else{
                    //视频播放
                    buttonPlay.setVisibility(View.GONE);
                    imageViewShow.setVisibility(View.GONE);
                    playVideo();
                }
                break;
            case R.id.title_left:
                finish();
                break;
            case R.id.cancelTextView:
                IntentTools.startMain(this);
                finish();
                break;
            case R.id.pushTextView:
//                PopUpDialog();
//                PushQuestion();
                UploadFile();
                break;
//            case R.id.quanzilayout:
//                IntentTools.startquanzilist(this, circleCode);
//                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
//                    videoViewShow.pause();
//                    buttonPlay.setVisibility(View.VISIBLE);
//                }
//                break;
            case R.id.hindlayout:
                IntentTools.startCreateTop(this, 1216);
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.typeSelectLayout:
                IntentTools.startSelectType(this, typeSelect);
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.sellTextView:
                ChangeTypeItem(true);
                break;
            case R.id.BuyTextView:
                ChangeTypeItem(false);
                break;
            case R.id.addressTv:
                IntentTools.startCityList(this,1010,"ispub");
                if(videoViewShow.isPlaying()&&videoViewShow.canPause()){
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    private void ChangeTypeItem(boolean isSell) {
        if (isSell) {
            Selletype = 1;
            sellTextView.setBackgroundResource(R.drawable.activity_select_item_select);
            BuyTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
            sellTextView.setTextColor(getResources().getColor(R.color.white));
            BuyTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
        } else {
            Selletype = 2;
            sellTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
            BuyTextView.setBackgroundResource(R.drawable.activity_select_item_select);
            sellTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
            BuyTextView.setTextColor(getResources().getColor(R.color.white));
        }
    }
    public void UploadFile() {

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

                Logger.e("resultddddd" + result);
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    JSONObject ja = jsonObject.getJSONObject("data");
                    String Urls = ja.getString("url");
                    String url_short = ja.getString("url_short");

                    String id = ja.getString("id");
                    Logger.e("ddd" + id + Urls + url_short);
                    PushQuestion(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new File(path), HttpConfig.uploadVideoFile);


    }

    private void PushQuestion(String id) {
        String titleTextVisewStr = titleTextVisew.getText().toString();
        String titleEtStr = titleEt.getText().toString();
        if (variety_id.equals("")) {
            ToastTool.show("请选择品种");
            return;
        }
        if (titleTextVisewStr.equals("")) {
            ToastTool.show("请填写标题");
            return;
        }
        if (titleEtStr.equals("")) {
            ToastTool.show("请填写内容");
            return;
        }

//        if(model.getName().equals("")){
//            ToastTool.show("请选择城市");
//            return;
//        }
//    public void PushGongQiu(ViewNetCallBack callBack, String variety_id, String title, String contents, String imgs,int type ,String city_id,String city_name,String province,String videosid) {
        PushController.getInstance().PushGongQiu(new ViewNetCallBack() {
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
                    Log.e("dffdf", object.toString());
                    if (!object.getBoolean("status")) {
                        ToastTool.show(object.getString("message"));
                    } else {
                        UserPreference.setISPOSTFINISHPOSR("1234we");
//                        PopUpDialog();
                        ShenGuiApplication.getInstance().clearAcCach();
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, variety_id, titleTextVisew.getText().toString(), titleEt.getText().toString(), "", Selletype, model.getId(),model.getName(),"",id);
    }

    private static final int circleCode = 10023;
    private int CircleId;
    private String titleString = "";
    private String titleid = "";
    private String variety_id = "";  //圈子
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        if (requestCode == 1216) {
            if (resultCode == 1010) {
                Log.e("mods-------------------", data.getStringExtra("topic") + data.getStringExtra("result"));
                titleString = data.getStringExtra("topic");
                titleid = data.getStringExtra("result");
                ticic.setText(titleString);
            }
        }
        if (requestCode == circleCode) {
            if (resultCode == 1010) {
                Log.e("modelsssss", data.getStringExtra("modelist"));
                CircleId = Integer.parseInt((String) data.getStringExtra("modelist"));
            }
        }
        if (resultCode == 1000) {
            if (requestCode == typeSelect) {
                Logger.e("date" + data.getSerializableExtra("variety_id"));
                variety_id = (String) data.getSerializableExtra("variety_id");
                selectImageTv.setText((String) data.getSerializableExtra("variety_type"));
            }
        }
        if(videoViewShow.isPlaying()){
            buttonPlay.setVisibility(View.GONE);
            imageViewShow.setVisibility(View.GONE);
            playVideo();
        }
    }

    //发布成功弹窗
    public void PopUpDialog() {   //弹框
        final SgActivityPushSuccessDialog PopUpDialogs = new SgActivityPushSuccessDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
//        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){
//            hindlayout.setVisibility(View.VISIBLE);
//        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){
//            hindlayout.setVisibility(View.GONE);
//        }
    }
}
