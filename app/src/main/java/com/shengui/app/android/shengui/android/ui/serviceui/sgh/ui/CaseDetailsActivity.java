package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.MessageSendViewNew;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgLoginActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHCaseDetailChatRecylerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHCaseDetailImageGirdAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorDateBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryItemBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryMsgBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UploadBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.WeiXinPayDean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SaveChatResultBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.GridSpacingItemDecoration;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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


public class CaseDetailsActivity extends SGUHBaseActivity implements MessageSendViewNew.MessageSendViewDelegate {


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
    @Bind(R.id.case_details_recyler_view)
    RecyclerView caseDetailsRecylerView;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.user_pump)
    LinearLayout userPump;
    @Bind(R.id.doctor_and_msg)
    LinearLayout doctorAndMsg;
    @Bind(R.id.user_collect)
    TextView userCollect;
    @Bind(R.id.user_Change)
    LinearLayout userChange;
    @Bind(R.id.message_send_view)
    MessageSendViewNew messageSendView;
    @Bind(R.id.chat)
    FrameLayout chat;
    @Bind(R.id.user_video_play)
    JCVideoPlayerStandard userVideoPlay;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;
    @Bind(R.id.tucao)
    TextView tucao;
    @Bind(R.id.user_gave)
    TextView userGave;
    @Bind(R.id.my_input_back)
    ImageView myInputBack;
    @Bind(R.id.editTextInput)
    EditText editTextInput;
    @Bind(R.id.my_input_tv)
    TextView myInputTv;
    @Bind(R.id.my_input_text)
    LinearLayout myInputText;
    @Bind(R.id.doctor_go)
    LinearLayout doctorGo;
    private long audioDuration = 0;

    private SGHCaseDetailImageGirdAdapter sghCaseDetailImageGirdAdapter;

    private TextView gaveDialogConfirm;
    private RadioGroup gaveRG;
    private ImageView dialogBack;
    private TextView teacherName;
    private ImageView teacherImage;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private EditText editText;

    private String price;

    int fav = -1;
    int status = -1;

    InquiryItemBean.DataBean data;
    DoctorDateBean.DataBean doctorData;
    String doctor;

    List<InquiryMsgBean.DataBean> inquiryMsg = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INQUIRYITEMBEAN:
                    final InquiryItemBean.DataBean databean = (InquiryItemBean.DataBean) msg.obj;
                    data = databean;

                    if (databean.getDoctor() != null) {
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
                                DoctorDateBean.DataBean dataBean = SGHJsonUtil.doctorData(CaseDetailsActivity.this, data.getDoctor(), 3);
                                Message message = handler.obtainMessage();
                                message.obj = dataBean;
                                message.what = DOCTORBAEN;
                                handler.sendMessage(message);
                            }
                        });
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
                                List<InquiryMsgBean.DataBean> dataBeen = SGHJsonUtil.inquiryMsg(CaseDetailsActivity.this, inquiryId);
                                Message message = handler.obtainMessage();
                                message.obj = dataBeen;
                                message.what = MSGBAEN;
                                handler.sendMessage(message);
                            }
                        });
                    }

                    if (databean.getStatus() == 0 || databean.getStatus() == 9) {
                        doctorAndMsg.setVisibility(View.GONE);
                        chat.setVisibility(View.GONE);
                        userChange.setVisibility(View.GONE);
                        userCollect.setVisibility(View.GONE);
                        userPump.setVisibility(View.GONE);
                    } else if (databean.getStatus() == 3) {
                        doctorAndMsg.setVisibility(View.VISIBLE);
                        userChange.setVisibility(View.GONE);
                        chat.setVisibility(View.GONE);
                        userCollect.setVisibility(View.VISIBLE);
                        userPump.setVisibility(View.GONE);
                    } else if (databean.getStatus() == 2) {
                        doctorAndMsg.setVisibility(View.VISIBLE);
                        userChange.setVisibility(View.VISIBLE);
                        chat.setVisibility(View.GONE);
                        userCollect.setVisibility(View.GONE);
                        userPump.setVisibility(View.VISIBLE);
                    } else {
                        doctorAndMsg.setVisibility(View.VISIBLE);
                        chat.setVisibility(View.GONE);
                        userChange.setVisibility(View.GONE);
                        userCollect.setVisibility(View.GONE);
                        userPump.setVisibility(View.GONE);
                    }

                    fav = databean.getFav();
                    status = databean.getStatus();
                    if (databean.getFav() != 0) {
                        userCollect.setBackgroundColor(getResources().getColor(R.color.gray));
                        userCollect.setText("取消收藏");
                    } else {
                        userCollect.setBackgroundColor(getResources().getColor(R.color.main_color));
                        userCollect.setText("收藏案例");
                    }

                    Glide.with(CaseDetailsActivity.this)
                            .load(Api.SGHBaseUrl + databean.getUserFace())
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(userFace);

                    userName.setText(databean.getUserName());
                    userTime.setText(CreateTimeUtil.time(databean.getCreateTime(), 2));
                    userViewsCounts.setText(databean.getViews() + "");
                    userIntro.setText(databean.getIntro());
                    if (databean.getContentType() == 1) {
                        userImageGird.setVisibility(View.GONE);
                        userVideo.setVisibility(View.GONE);
                    } else if (databean.getContentType() == 2) {
                        if (databean.getFiles() != null) {
                            userImageGird.setVisibility(View.VISIBLE);
                            userVideo.setVisibility(View.GONE);
                            userImageGird.setLayoutManager(new GridLayoutManager(CaseDetailsActivity.this, 3));
                            userImageGird.addItemDecoration(new GridSpacingItemDecoration(3, 16, true));
                            userImageGird.setHasFixedSize(true);
                            sghCaseDetailImageGirdAdapter = new SGHCaseDetailImageGirdAdapter(databean.getFiles(), CaseDetailsActivity.this);
                            userImageGird.setAdapter(sghCaseDetailImageGirdAdapter);
                            sghCaseDetailImageGirdAdapter.notifyDataSetChanged();
                        } else {
                            userImageGird.setVisibility(View.GONE);
                            userVideo.setVisibility(View.GONE);
                        }
                    } else if (databean.getContentType() == 3) {
                        if (databean.getFiles() != null) {
                            userImageGird.setVisibility(View.GONE);
                            userVideo.setVisibility(View.VISIBLE);
                            userVideoPlay.setUp(Api.baseUrl+databean.getFiles().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                            videoCovers(Api.baseUrl + databean.getFiles().get(0));
//                            userVideoPlay.thumbImageView.setImageBitmap(createVideoThumbnail(Api.baseUrl + databean.getFiles().g), 192, 108));
                        } else {
                            userImageGird.setVisibility(View.GONE);
                            userVideo.setVisibility(View.GONE);
                        }
                    }
                    break;

                case DOCTORBAEN:
                    DoctorDateBean.DataBean dataBean = (DoctorDateBean.DataBean) msg.obj;
                    doctorData = dataBean;
                    dialogControl();
                    Glide.with(CaseDetailsActivity.this)
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
                    Log.e("test", "handleMessage: " + data.getDoctor());
                    sghCaseDetailChatRecylerViewAdapter = new SGHCaseDetailChatRecylerViewAdapter(dataBeen, CaseDetailsActivity.this, data.getDoctor());
                    caseDetailsRecylerView.setAdapter(sghCaseDetailChatRecylerViewAdapter);
                    sghCaseDetailChatRecylerViewAdapter.notifyDataSetChanged();
                    break;
                case INQUIRYCOLLECT:
                    boolean bCancel = (boolean) msg.obj;
                    if (bCancel) {
                        Toast.makeText(CaseDetailsActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                        userCollect.setBackgroundColor(getResources().getColor(R.color.main_color));
                        userCollect.setText("收藏案例");
                        fav = 0;
                    } else {
                        Toast.makeText(CaseDetailsActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case INQUIRYCANCELCOLLECT:
                    boolean bCollect = (boolean) msg.obj;
                    if (bCollect) {
                        Toast.makeText(CaseDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        fav = 1;
                        userCollect.setBackgroundColor(getResources().getColor(R.color.gray));
                        userCollect.setText("取消收藏");
                    } else {
                        Toast.makeText(CaseDetailsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
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
                        Toast.makeText(CaseDetailsActivity.this, "发送失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        if (obj.getContentType() == 4 && audioDuration != 0) {
                            obj.setMediaTime((int) (audioDuration / 1000));
                            audioDuration = 0;
                        }
                        inquiryMsg.add(obj);
                        sghCaseDetailChatRecylerViewAdapter = new SGHCaseDetailChatRecylerViewAdapter(inquiryMsg, CaseDetailsActivity.this, data.getDoctor());
                        caseDetailsRecylerView.setAdapter(sghCaseDetailChatRecylerViewAdapter);
                        sghCaseDetailChatRecylerViewAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                        nestedScrollview.post(new Runnable() {
                            @Override
                            public void run() {
                                nestedScrollview.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                        caseDetailsRecylerView.smoothScrollToPosition(sghCaseDetailChatRecylerViewAdapter.getItemCount() - 1);

                    }
                    break;

                case TUCAOCALLBACK:
                    boolean aBoolean = (Boolean) msg.obj;
                    if (aBoolean) {
                        Toast.makeText(CaseDetailsActivity.this, "吐槽成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CaseDetailsActivity.this, "吐槽失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case WEIXINGAVE:

                    WeiXinPayDean weiXinPayDean = (WeiXinPayDean) msg.obj;
                    WeiXinPayDean.DataBean wxData = weiXinPayDean.getData();
                    if (weiXinPayDean.getStatus() == 1) {
                        IWXAPI api = WXAPIFactory.createWXAPI(CaseDetailsActivity.this, Constant.WXIDAPP_ID);
                        api.registerApp(Constant.WXIDAPP_ID);
                        PayReq payReq = new PayReq();
                        payReq.appId = Constant.WXIDAPP_ID;
                        payReq.partnerId = wxData.getPartnerid();
                        payReq.prepayId = wxData.getPrepayid();
                        payReq.packageValue = wxData.getPackageX();
                        payReq.nonceStr = wxData.getNoncestr();
                        ;
                        payReq.timeStamp = String.valueOf(wxData.getTimestamp());
                        payReq.sign = wxData.getSign();
                        api.sendReq(payReq);
                    } else {
                        runDialog.dismiss();
                        Toast.makeText(CaseDetailsActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case WEIXINCHECK:
                    boolean b = (boolean) msg.obj;
                    Log.e("test", "handleMessage: " + b);
                    runDialog.dismiss();
                    if (b) {
                        finish();
                    } else {
                        Toast.makeText(CaseDetailsActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case VIDEOCOVERS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    userVideoPlay.thumbImageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    private final int INQUIRYITEMBEAN = 1;
    private final int DOCTORBAEN = 2;
    private final int MSGBAEN = 3;
    private final int INQUIRYCANCELCOLLECT = 4;
    private final int INQUIRYCOLLECT = 5;
    private final int UPLOADAUDIO = 6;
    private final int UPLOADIMAGE = 7;
    private final int UPLOADVIDEO = 8;
    private final int SAVECHAT = 9;
    private final int TUCAOCALLBACK = 10;
    private final int WEIXINGAVE = 11;
    private final int WEIXINCHECK = 12;
    private final int VIDEOCOVERS = 13;

    private String inquiryId;
    private InputMethodManager methodManager;
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
    private Dialog runDialog;
    private VideoReceiver videoReceiver;
    private AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_case_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        inquiryId = intent.getStringExtra("inquiryId");

        diaglogCreate();

        registerReceiveInit();

        runDialog = com.shengui.app.android.shengui.android.ui.utilsview.DialogFacory.getInstance().createRunDialog(this, "正在提交。。");

        caseDetailsRecylerView.setLayoutManager(new LinearLayoutManager(CaseDetailsActivity.this, LinearLayoutManager.VERTICAL, false));

        initView();

        dataInit();

        ViewOnClick();

    }

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

    private void dataInit() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                InquiryItemBean.DataBean dataBean = SGHJsonUtil.inquiryItem(CaseDetailsActivity.this, inquiryId);
                Message message = handler.obtainMessage();
                message.obj = dataBean;
                message.what = INQUIRYITEMBEAN;
                handler.sendMessage(message);
            }
        });
    }

    private void ViewOnClick() {
        userPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat.setVisibility(View.VISIBLE);
                userChange.setVisibility(View.GONE);
                userPump.setVisibility(View.GONE);
            }
        });

        userCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FormBody formBody = new FormBody.Builder()//创建表单构造器
                        .add("inquiryId", inquiryId)//添加表单参数
                        .build();//生成简易表单型RequestBody

                if (fav > 0) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean b = SGHJsonUtil.collectInquiry(CaseDetailsActivity.this, formBody, 2);
                            Message message = handler.obtainMessage();
                            message.obj = b;
                            message.what = INQUIRYCOLLECT;
                            handler.sendMessage(message);
                        }
                    });

                } else if (fav == 0) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean b = SGHJsonUtil.collectInquiry(CaseDetailsActivity.this, formBody, 1);
                            Message message = handler.obtainMessage();
                            message.obj = b;
                            message.what = INQUIRYCANCELCOLLECT;
                            handler.sendMessage(message);
                        }
                    });

                } else if (!User.Login) {
                    startActivity(new Intent(CaseDetailsActivity.this, SgLoginActivity.class));
                    Toast.makeText(CaseDetailsActivity.this, "您还未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        doctorGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaseDetailsActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("doctorId", data.getDoctor());
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInputText.setVisibility(View.VISIBLE);
                userChange.setVisibility(View.GONE);
                inputControl(inquiryId);
                editTextInput.requestFocus();
                InputMethodManager imm = (InputMethodManager) editTextInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });

        userGave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });


    }

    private void registerReceiveInit() {
        // 注册广播接收
        videoReceiver = new VideoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("weixinpay");
        filter.addAction("deleteweixinpay");//只有持有相同的action的接受者才能接收此广播
        registerReceiver(videoReceiver, filter);
    }

    public class VideoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("weixinpay")) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        Boolean aBoolean = JsonUitl.weiXinCheck(context);
                        Message message = handler.obtainMessage();
                        message.obj = aBoolean;
                        message.what = WEIXINCHECK;
                        handler.sendMessage(message);
                    }
                });
            } else if (action.equals("deleteweixinpay")) {
                runDialog.dismiss();
            }
        }
    }

    private void diaglogCreate() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.sgu_dialog_gave, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.testDlg);
        alertDialog = builder
                .setView(contentView)
                .create();

        teacherImage = (ImageView) contentView.findViewById(R.id.gave_teacher_face);
        teacherName = (TextView) contentView.findViewById(R.id.gave_teacher_name);
        dialogBack = (ImageView) contentView.findViewById(R.id.gave_dialog_back);
        editText = (EditText) contentView.findViewById(R.id.gave_price_edit);
        gaveRG = (RadioGroup) contentView.findViewById(R.id.gave_rg);
        gaveDialogConfirm = (TextView) contentView.findViewById(R.id.gave_dialog_confirm);
        radioButton1 = (RadioButton) contentView.findViewById(R.id.gave_one);
        radioButton2 = (RadioButton) contentView.findViewById(R.id.gave_two);
        radioButton3 = (RadioButton) contentView.findViewById(R.id.gave_three);
        price = "5";
    }

    private void dialogControl() {


        teacherName.setText(doctorData.getTruename());
        Glide.with(this)
                .load(Uri.parse(Api.baseUrl + doctorData.getAvatar()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(teacherImage);

        gaveRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.gave_one:
                        price = "1";
                        editText.clearFocus();
                        break;
                    case R.id.gave_two:
                        price = "5";
                        editText.clearFocus();
                        break;
                    case R.id.gave_three:
                        price = "10";
                        editText.clearFocus();
                        break;
                }
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("5")) {
                    price = "5";
                    radioButton2.setChecked(true);
                } else if (s.toString().equals("1")) {
                    price = "1";
                    radioButton1.setChecked(true);
                } else if (s.toString().equals("10")) {
                    price = "10";
                    radioButton3.setChecked(true);
                } else {
                    price = editText.getText().toString();
                    gaveRG.clearCheck();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        gaveDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runDialog.show();
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        WeiXinPayDean weiXinPayDean = SGHJsonUtil.weixinGave(CaseDetailsActivity.this, inquiryId, price);
                        Message message = handler.obtainMessage();
                        message.what = WEIXINGAVE;
                        message.obj = weiXinPayDean;
                        handler.sendMessage(message);
                    }
                });
            }
        });


    }

    public void inputControl(final String inquiryId) {

        myInputBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextInput.setHint("");
                editTextInput.setText("");
                hideInput();
                myInputText.setVisibility(View.GONE);
            }
        });

        editTextInput.addTextChangedListener(new TextWatcher() {//监听输入内容变化，改变发表按钮颜色
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    myInputTv.setBackgroundResource(R.drawable.video_inout_tv_nature_shape);
                } else {
                    myInputTv.setBackgroundResource(R.drawable.video_inout_tv_select_shape);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {//软键盘发送按钮点击监听
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEND)) {
                    inputInit(inquiryId);
                    return true;
                }
                return false;
            }
        });

        myInputTv.setOnClickListener(new View.OnClickListener() {//点击发送监听
            @Override
            public void onClick(View v) {
                inputInit(inquiryId);
            }
        });
    }

    private void inputInit(final String inquiryId) {
        final String content = editTextInput.getText().toString();
        String trim = content.trim();
        if (trim.equals("")) {
            hideInput();
            Toast.makeText(CaseDetailsActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        } else {
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody = new FormBody.Builder()//创建表单构造器
                            .add("inquiryId", inquiryId)//添加表单参数
                            .add("content", content)
                            .build();//生成简易表单型RequestBody
                    Boolean ab = SGHJsonUtil.tucaoSave(CaseDetailsActivity.this, formBody);
                    Message message = handler.obtainMessage();
                    message.obj = ab;
                    message.what = TUCAOCALLBACK;
                    handler.sendMessage(message);
                }
            });
            editTextInput.setText("");
            editTextInput.setHint("");
            myInputText.setVisibility(View.GONE);
            hideInput();
        }
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


    protected void initView() {
//
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(CaseDetailsActivity.this, Manifest.permission.RECORD_AUDIO)) {
        } else {
            //提示用户开户权限音频
            String[] perms = {"android.permission.RECORD_AUDIO"};
            ActivityCompat.requestPermissions(CaseDetailsActivity.this, perms, 1202);
        }
        messageSendView.setDelegate(CaseDetailsActivity.this);

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
        methodManager.hideSoftInputFromWindow(((Activity) CaseDetailsActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

                    SaveChatResultBean chatResult = SGHJsonUtil.SaveChat(CaseDetailsActivity.this, formBody);
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

                    SaveChatResultBean chatResult = SGHJsonUtil.SaveChat(CaseDetailsActivity.this, formBody);
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
    public void touchSendTextMessageButton(final String text) {
        hideInput();
        dialog.show();
        saveChat(null, 1, text);
    }

    @Override
    public void touchSwitchButton() {

    }

    private int REQUESTCODE = 100;

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
                UploadBean uploadBean = SGHJsonUtil.uploadVideo(CaseDetailsActivity.this, path);
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
                .with(CaseDetailsActivity.this)
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
                                UploadBean uploadBean = SGHJsonUtil.uploadImg(CaseDetailsActivity.this, result.getThumbnailBigPath());
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
                                UploadBean uploadBean = SGHJsonUtil.uploadAudio(CaseDetailsActivity.this, audioFileName);
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


}
