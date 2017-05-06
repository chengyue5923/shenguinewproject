package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.MessageAdapter;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.MessageSendViewNew;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHCaseDetailChatRecylerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHCaseDetailImageGirdAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorDateBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryItemBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryMsgBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UploadBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SaveChatResultBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.GridSpacingItemDecoration;
import com.shengui.app.android.shengui.configer.constants.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.ImageCropBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.FormBody;


public class ReceivePatientCaseDetailsActivity extends AppCompatActivity implements MessageSendViewNew.MessageSendViewDelegate {


    SGHCaseDetailChatRecylerViewAdapter sghCaseDetailChatRecylerViewAdapter;
    @Bind(R.id.video_header)
    LinearLayout videoHeader;
    @Bind(R.id.user_face)
    CircleImageView userFace;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_time)
    TextView userTime;
    @Bind(R.id.user_views_counts)
    TextView userViewsCounts;
    @Bind(R.id.user_intro)
    TextView userIntro;
    @Bind(R.id.user_image_gird)
    RecyclerView userImageGird;
    @Bind(R.id.user_video)
    LinearLayout userVideo;
    @Bind(R.id.userQuestion)
    LinearLayout userQuestion;
    @Bind(R.id.doctor_face)
    CircleImageView doctorFace;
    @Bind(R.id.doctor_name)
    TextView doctorName;
    @Bind(R.id.doctor_memo)
    TextView doctorMemo;
    @Bind(R.id.doctor_numb)
    TextView doctorNumb;
    @Bind(R.id.doctor_pager)
    ImageView doctorPager;
    @Bind(R.id.doctor_details)
    LinearLayout doctorDetails;
    @Bind(R.id.case_details_recyler_view)
    RecyclerView caseDetailsRecylerView;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.user_pump)
    LinearLayout userPump;
    @Bind(R.id.doctor_and_msg)
    LinearLayout doctorAndMsg;
    @Bind(R.id.user_Change)
    LinearLayout userChange;
    @Bind(R.id.message_send_view)
    MessageSendViewNew messageSendView;
    @Bind(R.id.chat)
    FrameLayout chat;
    @Bind(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;
    @Bind(R.id.user_video_play)
    JCVideoPlayerStandard userVideoPlay;
    @Bind(R.id.back)
    LinearLayout back;


    private SGHCaseDetailImageGirdAdapter sghCaseDetailImageGirdAdapter;

    List<InquiryMsgBean.DataBean> inquiryMsg = new ArrayList<>();

    int fav = -1;
    int status = -1;


    private final int INQUIRYITEMBEAN = 1;
    private final int DOCTORBAEN = 2;
    private final int MSGBAEN = 3;
    private final int UPLOADAUDIO = 4;
    private final int UPLOADIMAGE = 5;
    private final int UPLOADVIDEO = 6;
    private final int INQUIRYCOLLECT = 7;
    private final int SAVECHAT = 8;
    private final int VIDEOCOVERS = 9;


    private String inquiryId;
    private String name;
    private String image;

    private MessageAdapter mAdapter;
    private String audioFileName;
    private float y1, y2;
    private Dialog soundVolumeDialog = null;
    private ImageView soundVolumeImg = null;
    private LinearLayout soundVolumeLayout = null;
    private Uri mImageCaptureUri;
    private long mAudioRecordStartTimeMS;
    private MediaRecorder recorder;
    private String sessionId = "";
    private int fromUserId;
    private static final int SELECT_IMAGE_RESULT_CODE = 999;
    private static final int CAPTURE_IMAGE_RESULT_CODE = 1000;
    private String touxiang;
    private Dialog dialog;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INQUIRYITEMBEAN:
                    InquiryItemBean.DataBean databean = (InquiryItemBean.DataBean) msg.obj;

                    Glide.with(ReceivePatientCaseDetailsActivity.this)
                            .load(Api.SGHBaseUrl + databean.getUserFace())
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(userFace);

                    userName.setText(databean.getUserName());
//                    userTime.setText(CreateTimeUtil.time(databean.getCreateTime(), 2));
                    userViewsCounts.setText(databean.getViews() + "");
                    userIntro.setText(databean.getIntro());
                    if (databean.getContentType() == 1) {
                        userImageGird.setVisibility(View.GONE);
                        userVideo.setVisibility(View.GONE);
                    } else if (databean.getContentType() == 2) {
                        userImageGird.setVisibility(View.VISIBLE);
                        userVideo.setVisibility(View.GONE);
                        userImageGird.setLayoutManager(new GridLayoutManager(ReceivePatientCaseDetailsActivity.this, 3));
                        userImageGird.addItemDecoration(new GridSpacingItemDecoration(3, 16, true));
                        userImageGird.setHasFixedSize(true);
                        sghCaseDetailImageGirdAdapter = new SGHCaseDetailImageGirdAdapter(databean.getFiles(), ReceivePatientCaseDetailsActivity.this);
                        userImageGird.setAdapter(sghCaseDetailImageGirdAdapter);
                        sghCaseDetailImageGirdAdapter.notifyDataSetChanged();
                    } else if (databean.getContentType() == 3) {
                        if (databean.getFiles() != null) {
                            userImageGird.setVisibility(View.GONE);
                            userVideo.setVisibility(View.VISIBLE);
                            userVideoPlay.setUp(Api.baseUrl + databean.getFiles().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                            videoCovers(Api.baseUrl + databean.getFiles().get(0));
//                            userVideoPlay.thumbImageView.setImageBitmap(createVideoThumbnail(Api.baseUrl + databean.getFiles().get(0), 192, 108));
                        } else {
                            userImageGird.setVisibility(View.GONE);
                            userVideo.setVisibility(View.GONE);
                        }
                    }

                    break;

                case DOCTORBAEN:
                    DoctorDateBean.DataBean dataBean = (DoctorDateBean.DataBean) msg.obj;
                    Glide.with(ReceivePatientCaseDetailsActivity.this)
                            .load(Api.SGHBaseUrl + dataBean.getAvatar())
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(doctorFace);
                    doctorName.setText(dataBean.getTruename());
                    doctorMemo.setText(dataBean.getSignature());
                    doctorNumb.setText(dataBean.getInquiryCount() + "");
                    break;
                case MSGBAEN:
                    List<InquiryMsgBean.DataBean> dataBeen = (List<InquiryMsgBean.DataBean>) msg.obj;
                    inquiryMsg.addAll(dataBeen);
                    sghCaseDetailChatRecylerViewAdapter = new SGHCaseDetailChatRecylerViewAdapter(inquiryMsg, ReceivePatientCaseDetailsActivity.this, User.userId);
                    caseDetailsRecylerView.setAdapter(sghCaseDetailChatRecylerViewAdapter);
                    sghCaseDetailChatRecylerViewAdapter.notifyDataSetChanged();
                    break;
                case UPLOADAUDIO:
                    UploadBean audioBean = (UploadBean) msg.obj;
                    saveChat(audioBean, 4, null);
                    break;
                case UPLOADIMAGE:
                    UploadBean IMAGEBean = (UploadBean) msg.obj;
                    saveChat(IMAGEBean, 2, null);
                    break;
                case UPLOADVIDEO:
                    UploadBean VIDEOBean = (UploadBean) msg.obj;
                    saveChat(VIDEOBean, 3, null);
                    break;
                case SAVECHAT:
                    InquiryMsgBean.DataBean obj = (InquiryMsgBean.DataBean) msg.obj;
                    if (obj == null) {
                        Toast.makeText(ReceivePatientCaseDetailsActivity.this, "发送失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        if (obj.getContentType() == 4 && audioDuration != 0) {
                            obj.setMediaTime((int) (audioDuration / 1000));
                            audioDuration = 0;
                        }
                        inquiryMsg.add(obj);
                        sghCaseDetailChatRecylerViewAdapter = new SGHCaseDetailChatRecylerViewAdapter(inquiryMsg, ReceivePatientCaseDetailsActivity.this, obj.getUserId());
                        caseDetailsRecylerView.setAdapter(sghCaseDetailChatRecylerViewAdapter);
                        sghCaseDetailChatRecylerViewAdapter.notifyDataSetChanged();
                        nestedScrollview.post(new Runnable() {
                            @Override
                            public void run() {
                                nestedScrollview.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                        dialog.dismiss();
                    }
                    break;

                case VIDEOCOVERS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    userVideoPlay.thumbImageView.setImageBitmap(bitmap);
                    break;

            }
        }
    };
    private long audioDuration = 0;
    private InputMethodManager methodManager;
    private int REQUESTCODE = 100;

    private void videoCovers(final String url) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());
                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(5 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                Message message = handler.obtainMessage();
                message.obj = bitmap;
                message.what = VIDEOCOVERS;
                handler.sendMessage(message);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }


    private void saveChat(final UploadBean uploadBean, final int type, final String content) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
//                "上传：（file：数据源；type类型：inquiry）返回路径
//                上传：（inquiryId：问诊ID；contentType：提交类型；content：内容；media：路径）"
                Boolean b = false;
                if (type == 1) {
                    FormBody formBody = new FormBody.Builder()//创建表单构造器
                            .add("inquiryId", inquiryId)
                            .add("content", content)//添加表单参数
                            .add("contentType", String.valueOf(type))
                            .build();//生成简易表单型RequestBody

                    SaveChatResultBean chatResult = SGHJsonUtil.SaveChat(ReceivePatientCaseDetailsActivity.this, formBody);
                    SaveChatResultBean.DataBean data = chatResult.getData();
                    InquiryMsgBean.DataBean dataBean = null;
                    if (chatResult.getStatus() == 1) {
                        dataBean = new InquiryMsgBean.DataBean(data.getId(), data.getMedia(), data.getUserFace(), data.getUserName(), data.getUserId(), data.getCreateTime(), data.getContent(), data.getInquiryId(), Integer.valueOf(data.getContentType()));
                    } else {
                        dataBean = null;
                    }
                    Message message = handler.obtainMessage();
                    message.what = SAVECHAT;
                    message.obj = dataBean;
                    handler.sendMessage(message);
                } else {
                    String files = uploadBean.getData();

                    FormBody formBody = new FormBody.Builder()//创建表单构造器
//                            .add("content", content)//添加表单参数
                            .add("inquiryId", inquiryId)
                            .add("media", files)
                            .add("contentType", String.valueOf(type))
                            .build();//生成简易表单型RequestBody

                    SaveChatResultBean chatResult = SGHJsonUtil.SaveChat(ReceivePatientCaseDetailsActivity.this, formBody);
                    SaveChatResultBean.DataBean data = chatResult.getData();
                    InquiryMsgBean.DataBean dataBean = null;
                    if (chatResult.getStatus() == 1) {
                        dataBean = new InquiryMsgBean.DataBean(data.getId(), data.getMedia(), data.getUserFace(), data.getUserName(), data.getUserId(), data.getCreateTime(), data.getContent(), data.getInquiryId(), Integer.valueOf(data.getContentType()));
                    } else {
                        dataBean = null;
                    }
                    Message message = handler.obtainMessage();
                    message.what = SAVECHAT;
                    message.obj = dataBean;
                    handler.sendMessage(message);
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_rp_case_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        inquiryId = intent.getStringExtra("inquiryId");
        name = intent.getStringExtra("userName");
        image = intent.getStringExtra("userImage");

        userPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat.setVisibility(View.VISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        LinearLayoutManager layout = new LinearLayoutManager(ReceivePatientCaseDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        layout.setStackFromEnd(true);
        caseDetailsRecylerView.setLayoutManager(layout);

        initView();

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                InquiryItemBean.DataBean dataBean = SGHJsonUtil.inquiryItem(ReceivePatientCaseDetailsActivity.this, inquiryId);
                Message message = handler.obtainMessage();
                message.obj = dataBean;
                message.what = INQUIRYITEMBEAN;
                handler.sendMessage(message);
            }
        });

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<InquiryMsgBean.DataBean> dataBeen = SGHJsonUtil.inquiryMsg(ReceivePatientCaseDetailsActivity.this, inquiryId);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = MSGBAEN;
                handler.sendMessage(message);
            }
        });

    }


    protected void initView() {
//
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(ReceivePatientCaseDetailsActivity.this, Manifest.permission.RECORD_AUDIO)) {
        } else {
            //提示用户开户权限音频
            String[] perms = {"android.permission.RECORD_AUDIO"};
            ActivityCompat.requestPermissions(ReceivePatientCaseDetailsActivity.this, perms, 1202);
        }
        messageSendView.setDelegate(ReceivePatientCaseDetailsActivity.this);

        initSoundVolumeDlg();

        dialog = DialogFacory.getInstance().createRunDialog(this, "正在发送。。");
    }

    /**
     * @Description 初始化音量对话框
     */
    private void initSoundVolumeDlg() {
        soundVolumeDialog = new Dialog(this, R.style.SoundVolumeStyle);
        soundVolumeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        soundVolumeDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        soundVolumeDialog.setContentView(R.layout.im_sound_volume_dialog);
        soundVolumeDialog.setCanceledOnTouchOutside(true);
        soundVolumeImg = (ImageView) soundVolumeDialog.findViewById(R.id.sound_volume_img);
        soundVolumeLayout = (LinearLayout) soundVolumeDialog.findViewById(R.id.sound_volume_bk);
    }

    private void hideInput() {
        methodManager = ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow(((Activity) ReceivePatientCaseDetailsActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void touchSendTextMessageButton(final String text) {
        hideInput();
        dialog.show();
        saveChat(null, 1, text);
    }

    @Override
    public void touchSwitchButton() {

    }

    @Override
    public void touchPickCameraPhotoButton() {
        startActivityForResult(new Intent(this, SGHMGSVideoActivity.class), REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String path = data.getStringExtra("result");
        dialog.show();
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                UploadBean uploadBean = SGHJsonUtil.uploadVideo(ReceivePatientCaseDetailsActivity.this, path);
                Message message = handler.obtainMessage();
                message.obj = uploadBean;
                message.what = UPLOADVIDEO;
                handler.sendMessage(message);
            }
        });
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void touchLibraryPhotoButton() {
        //自定义方法的单选
        RxGalleryFinal
                .with(ReceivePatientCaseDetailsActivity.this)
                .image()
                .radio()
                .crop()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        final ImageCropBean result = imageRadioResultEvent.getResult();
                        result.getThumbnailBigPath();
                        dialog.show();
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
                                UploadBean uploadBean = SGHJsonUtil.uploadImg(ReceivePatientCaseDetailsActivity.this, result.getThumbnailBigPath());
                                Message message = handler.obtainMessage();
                                message.obj = uploadBean;
                                message.what = UPLOADIMAGE;
                                handler.sendMessage(message);
                            }
                        });

                    }
                })
                .openGallery();
    }

    @Override
    public void touchDownAudioRecordButton(MotionEvent event) {

        Log.e("test", "touchDownAudioRecordButton: " + event);
        if (soundVolumeDialog.isShowing()) {
            return;
        }
        audioFileName = Constant.AUDIO_TEMP_PATH + File.separator + new Date().getTime() + ".amr";
        Log.e("test", "touchDownAudioRecordButton: " + audioFileName);
        //stop video and audio player
        mAudioRecordStartTimeMS = System.currentTimeMillis();
        Log.e("test", "touchDownAudioRecordButton: " + mAudioRecordStartTimeMS);
        start(audioFileName);
        y1 = event.getY();
        soundVolumeImg.setImageResource(R.drawable.im_sound_volume_01);
        soundVolumeImg.setVisibility(View.VISIBLE);
        soundVolumeLayout.setBackgroundResource(R.drawable.i_sound_volume_default_bk);
        soundVolumeDialog.show();
    }

    private void start(String path) {
        try {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            recorder.setAudioChannels(2);
            recorder.setAudioSamplingRate(8000);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            recorder.setOutputFile(path);
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
        }
    }

    @Override
    public void touchMoveAudioRecordButton(MotionEvent event) {
        y2 = event.getY();
        if (y1 - y2 > 180) {
            soundVolumeImg.setVisibility(View.GONE);
            soundVolumeLayout.setBackgroundResource(R.drawable.im_sound_volume_cancel_bk);
        } else {
            soundVolumeImg.setVisibility(View.VISIBLE);
            soundVolumeLayout.setBackgroundResource(R.drawable.i_sound_volume_default_bk);
        }
    }

    @Override
    public void touchUpAudioRecordButton(MotionEvent event) {
        if (soundVolumeDialog.isShowing()) {
            soundVolumeDialog.dismiss();

        }
        try {
            if (Math.abs(y1 - y2) <= 180) {
                final long msDuration = System.currentTimeMillis() - mAudioRecordStartTimeMS;
                audioDuration = msDuration;
                if (msDuration >= 1000) {
                    if (msDuration < Constant.MAX_SOUND_RECORD_TIME) {
                        //// TODO: 2016/7/13 发送
//                    recorder.release();
                        dialog.show();
                        recorder.stop();
                        recorder.release();
                        recorder = null;

                        Log.e("test", "touchUpAudioRecordButton: " + audioFileName);
//                        new ChatActivity.UploadAudioTask(audioFileName, (int) msDuration / 1000).execute();
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
                                UploadBean uploadBean = SGHJsonUtil.uploadAudio(ReceivePatientCaseDetailsActivity.this, audioFileName);
                                Message message = handler.obtainMessage();
                                message.obj = uploadBean;
                                message.what = UPLOADAUDIO;
                                handler.sendMessage(message);
                            }
                        });
                    }
                } else {
                    soundVolumeImg.setVisibility(View.GONE);
                    soundVolumeLayout
                            .setBackgroundResource(R.drawable.im_sound_volume_short_tip_bk);
                    soundVolumeDialog.show();

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        public void run() {
                            if (soundVolumeDialog.isShowing())
                                soundVolumeDialog.dismiss();
                            this.cancel();
                        }
                    }, 500);
                }
            }
        } catch (Exception e) {

            Logger.e("exceptioin" + e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShenGuiApplication.getInstance().clearAcCach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
