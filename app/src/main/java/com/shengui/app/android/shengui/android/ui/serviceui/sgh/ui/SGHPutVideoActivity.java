package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

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
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UploadBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.utilsview.EditTextMultiLine;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;

/**
 * 视频认证上传界面
 * Created by Wood on 2016/4/7.
 */
public class SGHPutVideoActivity extends SGUHBaseActivity implements View.OnClickListener, View.OnLayoutChangeListener {

    private static final int RES_CODE = 111;

    /**
     * 播放进度
     */
    private static final int PLAY_PROGRESS = 110;
    private final int VIDEOUPLOAD = 3;
    private final int SAVEPOESTION=2;

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
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.pushTextView)
    TextView pushTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.titleEt)
    EditTextMultiLine titleEt;
    @Bind(R.id.videoView_show)
    VideoView videoViewShow;
    @Bind(R.id.imageView_show)
    ImageView imageViewShow;
    @Bind(R.id.button_play)
    Button buttonPlay;
    @Bind(R.id.addressTv)
    TextView addressTv;
    @Bind(R.id.bottom_publish)
    TextView bottomPublish;
    @Bind(R.id.rootlayout)
    LinearLayout rootlayout;
    private Dialog dialog;

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

                    //currentTime++;
                    //达到指定时间，停止播放
                    if (!videoViewShow.isPlaying() && time > 0) {
                        if (timer != null) {
                            timer.cancel();
                        }
                    }
                    break;
                case VIDEOUPLOAD:
                    UploadBean uploadBean = (UploadBean) msg.obj;
                    if (uploadBean.getStatus() == 1) {
                       SaveQuestion(uploadBean,3);
                    }
                    break;
                case SAVEPOESTION:
                    boolean b = (boolean) msg.obj;
                    if (b){
                        PopUpDialog();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(SGHPutVideoActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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

    private TextView textViewName;

    LocationModel model = new LocationModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_put_video);
        ButterKnife.bind(this);
        dialog = DialogFacory.getInstance().createRunDialog(this, "正在提交。。");
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        initView();
        initData();

//        if(!UserPreference.getTOPICID().equals("")){
//            CircleId = Integer.parseInt(UserPreference.getTOPICID());
//        }
//        textViewName.setText(UserPreference.getTOPICCONTENT());
    }

    private void SaveQuestion(final UploadBean uploadBean, final int type) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {

                String files = "";
                files = uploadBean.getData();

                FormBody formBody = new FormBody.Builder()//创建表单构造器
                        .add("intro", titleEt.getText().toString())//添加表单参数
                        .add("files", files)
                        .add("contentType", String.valueOf(type))
                        .build();//生成简易表单型RequestBody
                Boolean aBoolean = SGHJsonUtil.SaveQuestion(SGHPutVideoActivity.this, formBody);
                Message message = handler.obtainMessage();
                message.what = SAVEPOESTION;
                message.obj = aBoolean;
                handler.sendMessage(message);
            }
        });
    }

    public void initView() {
        ((TextView) findViewById(R.id.title)).setText("视频预览");
        findViewById(R.id.title_left).setOnClickListener(this);

        buttonPlay.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        bottomPublish.setOnClickListener(this);
        pushTextView.setOnClickListener(this);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) videoViewShow.getLayoutParams();
        layoutParams.height = width * 4 / 3;//根据屏幕宽度设置预览控件的尺寸，为了解决预览拉伸问题

        videoViewShow.setLayoutParams(layoutParams);
        imageViewShow.setLayoutParams(layoutParams);

    }

    public void initData() {
        Intent intent = getIntent();
        addressTv.setText(UserPreference.getCityName());
        if (intent != null) {
            path = "";
            path = intent.getExtras().getString("path", "");

            file = new File(path);

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
                if (videoViewShow.isPlaying() && videoViewShow.canPause()) {
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
                if (videoViewShow.isPlaying() && videoViewShow.canPause()) {
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                finish();
                ShenGuiApplication.getInstance().clearAcCach();
                break;
            case R.id.pushTextView:

                UploadFile();
                if (videoViewShow.isPlaying() && videoViewShow.canPause()) {
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.bottom_publish:
                UploadFile();
                if (videoViewShow.isPlaying() && videoViewShow.canPause()) {
                    videoViewShow.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    public void UploadFile() {


        String StrtitleEt = titleEt.getText().toString();
        if (StringTools.isNullOrEmpty(StrtitleEt)) {

            ToastTool.show("您还没有编辑内容");
            return;
        }

        dialog.show();

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                UploadBean uploadBean = SGHJsonUtil.uploadVideo(SGHPutVideoActivity.this, path);
                Message message = handler.obtainMessage();
                message.what = VIDEOUPLOAD;
                message.obj = uploadBean;
                handler.sendMessage(message);
            }
        });


    }


    private static final int circleCode = 10023;
    private int CircleId;
    private String titleString = "";
    private String titleid = "";

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
                    Intent intent = new Intent(SGHPutVideoActivity.this, SGHMyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
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
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShenGuiApplication.getInstance().clearAcCach();
    }
}
