package com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgLoginActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.JCVideoPlayerStandard;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoAllCommentsAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentVideoAboutV;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentVideoComment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentVideoGave;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentVideoIntroduction;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.AllcommentsBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.CommentsCallBackBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoCommentBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoPublishCommentBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.WeiXinPayDean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import okhttp3.FormBody;

public class VideoPlayActivity extends SGUHBaseActivity {


    @Bind(R.id.video_header)
    LinearLayout videoHeader;
    @Bind(R.id.video_play)
    JCVideoPlayerStandard videoPlay;
    @Bind(R.id.video_title)
    TextView videoTitle;
    @Bind(R.id.teacher_name)
    TextView videoTeacherName;
    @Bind(R.id.teacher_msg)
    LinearLayout teacherMsg;
    @Bind(R.id.video_watch_numb)
    TextView videoWatchNumb;
    @Bind(R.id.numbLL)
    LinearLayout numbLL;
    @Bind(R.id.video_time)
    TextView videoTime;
    @Bind(R.id.video_price)
    TextView videoPrice;
    @Bind(R.id.video_go_buy)
    TextView videoGoBuy;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.video_tabLayout)
    TabLayout videoTabLayout;
    @Bind(R.id.appbatlayout)
    AppBarLayout appbatlayout;
    @Bind(R.id.video_view_pager)
    ViewPager videoViewPager;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.video_all_comment_back)
    ImageView videoAllCommentBack;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.video_all_comment_lv)
    RecyclerView videoAllCommentLv;
    @Bind(R.id.video_all_comment)
    LinearLayout videoAllComment;
    @Bind(R.id.video_aa)
    LinearLayout videoAa;
    @Bind(R.id.editText)
    TextView editText;
    @Bind(R.id.video_collect)
    ImageView videoCollect;
    @Bind(R.id.video_shape)
    ImageView videoShape;
    @Bind(R.id.video_bottom_input)
    LinearLayout videoBottomInput;
    @Bind(R.id.video_input_back)
    ImageView videoInputBack;
    @Bind(R.id.editTextInput)
    EditText editTextInput;
    @Bind(R.id.video_input_tv)
    TextView videoInputTv;
    @Bind(R.id.video_input_text)
    LinearLayout videoInputText;
    @Bind(R.id.activity_video_play)
    SwipeRefreshLayout activityVideoPlay;
    @Bind(R.id.video_back)
    LinearLayout videoBack;

    private SurfaceHolder holder;

    static int position = -1;

    private String courseId;

    private final int VIDEOMSG = 1;
    private final int VIDEOCOMMENT = 2;

    private final int VIDEOCOLLECT = 3;
    private final int VIDEOCANCELCOLLECT = 4;

    private final int ALLCOMMENTS = 5;

    private final int COMMENTCALLBACK = 6;

    private final int VIDEOCOVERS = 7;

    private final int VIDEOCOMMENTCALLBACK = 8;

    private final int WEIXINPAY = 9;

    private final int WEIXINCHECK = 10;

    private boolean isAllComments = false;
    private String receiver = null;
    private int pos = -1;
    private int place = -1;
    int fav = -1;
    int allCommentPosition = -1;

    List<Fragment> fragments;
    List<AllcommentsBean.DataBean> allComments = new ArrayList<>();

    private VideoBean.DataBean data = null;
    VideoCommentBean.DataBeanX dataAllComments = null;
    private VideoAllCommentsAdapter videoAllCommentsAdapter;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case VIDEOMSG:
                    VideoBean.DataBean dataBean = (VideoBean.DataBean) msg.obj;
                    if (dataBean != null) {
                        data = dataBean;
                        videoPlay.setUp(Api.baseUrl + dataBean.getDir(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL, "");

                        Log.e("test", "handleMessage: " + Api.baseUrl + dataBean.getDir()+"  222"+(((double) dataBean.getPrice()))/100);

                        videoPlay.setOnCompletion(VideoPlayActivity.this, dataBean.getPrice(), dataBean.getBuy());
                        videoCovers(Api.baseUrl + dataBean.getDir());

                        if (dataBean.getPrice() > 0) {
                            videoPrice.setText("价格：¥" + (((double) dataBean.getPrice()))/100);
                        } else {
                            videoPrice.setText("价格：¥" + dataBean.getPrice());
                        }
                        if (dataBean.getBuy() == 0) {
                            videoGoBuy.setText("马上购买");
                        } else {
                            videoGoBuy.setText("已购买");
                        }
                        if (dataBean.getPrice() == 0) {
                            videoGoBuy.setVisibility(View.GONE);
                        } else {
                            videoGoBuy.setVisibility(View.VISIBLE);
                        }
                        videoTeacherName.setText(dataBean.getTeacherName());
                        videoTitle.setText(dataBean.getName());
                        videoWatchNumb.setText(dataBean.getViews() + "");
                        fav = dataBean.getFav();
                        if (dataBean.getFav() != 0) {
                            videoCollect.setBackgroundResource(R.mipmap.icon_share_active);
                        } else {
                            videoCollect.setBackgroundResource(R.mipmap.icon_collect);
                        }
                        String time = CreateTimeUtil.time(dataBean.getPublishTime(), 2);
                        String teacher = dataBean.getTeacher();
                        String intro = dataBean.getIntro();
                        VideoViewPageInit(teacher, intro);
                        videoTime.setText("发布于" + time);
                    }
                    break;

                case VIDEOCOMMENT:
                    VideoPublishCommentBean commentBean = (VideoPublishCommentBean) msg.obj;
                    VideoPublishCommentBean.DataBean data = commentBean.getData();
                    fragmentVideoComment.reflash();
                    VideoCommentBean.DataBeanX dataBeanX = new VideoCommentBean.DataBeanX(data.getCommentId(), data.getUserId(), data.getContent(), data.getReceiver(), data.getCreateTime(), 0, 0, User.userRealName, User.userFace);
                    fragmentVideoComment.addComment(dataBeanX, -1);
                    Toast.makeText(VideoPlayActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    isAllComments = false;//f发送完成清除记录
                    dataAllComments = null;
                    break;

                case VIDEOCOLLECT:
                    boolean b1 = (boolean) msg.obj;
                    if (b1) {
                        Toast.makeText(VideoPlayActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        videoCollect.setBackgroundResource(R.mipmap.icon_share_active);
                        fav = 1;
                    } else {
                        Toast.makeText(VideoPlayActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case VIDEOCANCELCOLLECT:
                    boolean b2 = (boolean) msg.obj;
                    if (b2) {
                        Toast.makeText(VideoPlayActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                        videoCollect.setBackgroundResource(R.mipmap.icon_collect);
                        fav = 0;
                    } else {
                        Toast.makeText(VideoPlayActivity.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case ALLCOMMENTS:
//                    allCommentsData(dataAllComments.getId());
                    List<AllcommentsBean.DataBean> allCommentBean = (List<AllcommentsBean.DataBean>) msg.obj;
                    allComments = allCommentBean;
                    videoAllCommentsAdapter = new VideoAllCommentsAdapter(VideoPlayActivity.this, allCommentBean);
                    videoAllCommentLv.setAdapter(videoAllCommentsAdapter);
                    videoAllCommentsAdapter.notifyDataSetChanged();
                    break;

                case COMMENTCALLBACK://回复接口返回参数设置
                    fragmentVideoComment.reflash();
                    CommentsCallBackBean.DataBean commentsCallBackData = (CommentsCallBackBean.DataBean) msg.obj;
                    List<VideoCommentBean.DataBeanX.DataBean> data1 = new ArrayList<>();
                    if (dataAllComments.getData() == null) {
                        data1.add(new VideoCommentBean.DataBeanX.DataBean(commentsCallBackData.getCommentId(), commentsCallBackData.getUserId(), commentsCallBackData.getReceiver(), commentsCallBackData.getReceiverRealName(), commentsCallBackData.getUserRealName(), commentsCallBackData.getContent(), commentsCallBackData.getCreateTime()));
                        dataAllComments.setData(data1);
                        fragmentVideoComment.changeComment(dataAllComments, place);
                    } else {
                        dataAllComments.getData().add(0, new VideoCommentBean.DataBeanX.DataBean(commentsCallBackData.getCommentId(), commentsCallBackData.getUserId(), commentsCallBackData.getReceiver(), commentsCallBackData.getReceiverRealName(), commentsCallBackData.getUserRealName(), commentsCallBackData.getContent(), commentsCallBackData.getCreateTime()));

                        if (dataAllComments.getData().size() > 3) {
                            fragmentVideoComment.changeComment(dataAllComments, place);
                        } else {
                            fragmentVideoComment.videoCommentAdapter.videoCommentCallRecylerViewAdapter.addComments(new VideoCommentBean.DataBeanX.DataBean(commentsCallBackData.getCommentId(), commentsCallBackData.getUserId(), commentsCallBackData.getReceiver(), commentsCallBackData.getReceiverRealName(), commentsCallBackData.getUserRealName(), commentsCallBackData.getContent(), commentsCallBackData.getCreateTime()));
                        }
                    }
                    isAllComments = false;//f发送完成清除记录
                    dataAllComments = null;
                    place = -1;
                    break;
                case VIDEOCOVERS:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    videoPlay.thumbImageView.setImageBitmap(bitmap);
                    break;
                case VIDEOCOMMENTCALLBACK:
                    allCommentsData(dataAllComments.getId());
                    break;

                case WEIXINPAY:
                    WeiXinPayDean weiXinPayDean = (WeiXinPayDean) msg.obj;
                    WeiXinPayDean.DataBean wxData = weiXinPayDean.getData();
                    if (weiXinPayDean.getStatus() == 1) {
                        IWXAPI api = WXAPIFactory.createWXAPI(VideoPlayActivity.this, Constant.WXIDAPP_ID);
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
                        Toast.makeText(VideoPlayActivity.this, "下单失败,请检查输入金额格式或稍后再试。。。", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case WEIXINCHECK:
                    boolean obj = (boolean) msg.obj;
                    runDialog.dismiss();
                    if (obj) {
                        httpDataBean();
                    } else {
                        Toast.makeText(VideoPlayActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    private VideoReceiver videoReceiver;
    private Dialog runDialog;


    public void control() {
        DialogFacory.getInstance().createAlertDialog(this, "提示", "付费后继续观看精彩内容...", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (User.Login) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            WeiXinPayDean weixinpay = JsonUitl.weixinpay(VideoPlayActivity.this, courseId);
                            Message message = handler.obtainMessage();
                            message.what = WEIXINPAY;
                            message.obj = weixinpay;
                            handler.sendMessage(message);
                        }
                    });
                }else {
                    startActivity(new Intent(VideoPlayActivity.this, SgLoginActivity.class));
                }
            }
        }, null).show();
    }


    public class VideoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("weixinpay")) {
                Log.e("test", "onReceive: " + action);
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        Boolean aBoolean = JsonUitl.weiXinCheck(VideoPlayActivity.this);
                        Message message = handler.obtainMessage();
                        message.obj = aBoolean;
                        message.what = WEIXINCHECK;
                        handler.sendMessage(message);
                    }
                });

            }
        }

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

    private FragmentVideoIntroduction fragmentVideoIntroduction;
    private FragmentVideoAboutV fragmentVideoAboutV;
    private FragmentVideoGave fragmentVideoGave;
    private FragmentVideoComment fragmentVideoComment;
    private InputMethodManager methodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgu_activity_video_play);
        ButterKnife.bind(this);


        runDialog = com.shengui.app.android.shengui.android.ui.utilsview.DialogFacory.getInstance().createRunDialog(VideoPlayActivity.this, "正在提交。。");

        dataReady();

        registerReceiveInit();

        httpDataBean();

        inputControl();

        viewOnClicek();
    }

    private void registerReceiveInit() {
        // 注册广播接收
        videoReceiver = new VideoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("weixinpay");    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(videoReceiver, filter);
    }

    private void viewOnClicek() {


        activityVideoPlay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        httpDataBean();
                        fragmentVideoComment.reflash();
                        fragmentVideoGave.reflash();

                        activityVideoPlay.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        appbatlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    activityVideoPlay.setEnabled(true);
                } else {
                    activityVideoPlay.setEnabled(false);
                }
            }
        });



        videoAllCommentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoAllComment.setVisibility(View.GONE);
                coordinatorLayout.setVisibility(View.VISIBLE);
                dataAllComments = null;
                isAllComments = false;

            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoBottomInput.setVisibility(View.GONE);
                videoInputText.setVisibility(View.VISIBLE);

                editTextInput.requestFocus();
                InputMethodManager imm = (InputMethodManager) editTextInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });


        videoCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FormBody formBody = new FormBody.Builder()//创建表单构造器
                        .add("courseId", courseId)//添加表单参数
                        .build();//生成简易表单型RequestBody

                if (fav > 0) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean b = JsonUitl.collectVideo(VideoPlayActivity.this, formBody, 2);
                            Message message = handler.obtainMessage();
                            message.obj = b;
                            message.what = VIDEOCANCELCOLLECT;
                            handler.sendMessage(message);
                        }
                    });

                } else if (fav == 0) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean b = JsonUitl.collectVideo(VideoPlayActivity.this, formBody, 1);
                            Message message = handler.obtainMessage();
                            message.obj = b;
                            message.what = VIDEOCOLLECT;
                            handler.sendMessage(message);
                        }
                    });

                } else {
                    startActivity(new Intent(VideoPlayActivity.this,SgLoginActivity.class));
                }
            }

        });

        videoGoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.Login) {
                    if (data.getBuy() == 0) {
                        runDialog.show();
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
                                WeiXinPayDean weixinpay = JsonUitl.weixinpay(VideoPlayActivity.this, courseId);
                                Message message = handler.obtainMessage();
                                message.what = WEIXINPAY;
                                message.obj = weixinpay;
                                handler.sendMessage(message);
                            }
                        });
                    }
                }else {
                    startActivity(new Intent(VideoPlayActivity.this,SgLoginActivity.class));
                }
            }
        });

        videoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void allCommentControl(final String pid, VideoCommentBean.DataBeanX dataBeanX) {
        videoAllComment.setVisibility(View.VISIBLE);
        coordinatorLayout.setVisibility(View.GONE);

        dataAllComments = dataBeanX;
        pos = -1;
        isAllComments = true;

        videoAllCommentLv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        allCommentsData(pid);

    }

    private void allCommentsData(final String pid) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<AllcommentsBean.DataBean> dataBeen = JsonUitl.allComment(VideoPlayActivity.this, courseId, pid);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = ALLCOMMENTS;
                handler.sendMessage(message);
            }
        });
    }

    private void httpDataBean() {
        final FormBody formBody = new FormBody.Builder()//创建表单构造器
                .add("courseId", courseId)//添加表单参数
                .build();//生成简易表单型RequestBody
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                VideoBean.DataBean dataBean = JsonUitl.videoMsg(VideoPlayActivity.this, courseId);
                boolean b = JsonUitl.userViewRecord(VideoPlayActivity.this, formBody);
                Message message = handler.obtainMessage();
                message.obj = dataBean;
                message.what = VIDEOMSG;
                handler.sendMessage(message);
            }
        });

    }

    public void inputControl() {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoBottomInput.setVisibility(View.GONE);
                videoInputText.setVisibility(View.VISIBLE);

                editTextInput.requestFocus();
                InputMethodManager imm = (InputMethodManager) editTextInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });

        videoInputBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoInputText.setVisibility(View.GONE);
                videoBottomInput.setVisibility(View.VISIBLE);
                editTextInput.setHint("");
                editTextInput.setText("");
                isAllComments = false;
                hideInput();
            }
        });

        editTextInput.addTextChangedListener(new TextWatcher() {//监听输入内容变化，改变发表按钮颜色
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    videoInputTv.setBackgroundResource(R.drawable.video_inout_tv_nature_shape);
                } else {
                    videoInputTv.setBackgroundResource(R.drawable.video_inout_tv_select_shape);
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
                    if (inputInit()) return true;
                }
                return false;
            }
        });

        videoInputTv.setOnClickListener(new View.OnClickListener() {//点击发送监听
            @Override
            public void onClick(View v) {
                inputInit();
            }
        });
    }


    private boolean inputInit() {

        String content = editTextInput.getText().toString();
        String trim = content.trim();
        if (trim.equals("")) {
            hideInput();
            Toast.makeText(VideoPlayActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();

        } else {
            if (isAllComments && dataAllComments != null) {//回复列表
//                            "courseId：视频ID； pid：上级评论ID；receiver：回复人ID；content：内容"
                String receiver = "";
                if (pos < 0) {
                    receiver = dataAllComments.getUserId();
                } else if (allCommentPosition > 0 && allComments != null) {
                    receiver = allComments.get(allCommentPosition).getUserId();//当全部评论打开，
                } else {
                    receiver = dataAllComments.getData().get(pos).getUserId();
                }

                Log.e("test", "inputInit: " + dataAllComments.getId());
                final FormBody formBody = new FormBody.Builder()//创建表单构造器
                        .add("courseId", courseId)//添加表单参数
                        .add("pid", dataAllComments.getId())
                        .add("receiver", receiver)
                        .add("content", content)
                        .build();//生成简易表单型RequestBody
                hideInput();

                if (videoAllComment.getVisibility() == View.VISIBLE) {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            CommentsCallBackBean.DataBean dataBean = JsonUitl.commentsCallBack(VideoPlayActivity.this, formBody);
                            Message message = handler.obtainMessage();
                            message.obj = dataBean;
                            message.what = VIDEOCOMMENTCALLBACK;
                            handler.sendMessage(message);
                        }
                    });
                } else {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            CommentsCallBackBean.DataBean dataBean = JsonUitl.commentsCallBack(VideoPlayActivity.this, formBody);
                            Message message = handler.obtainMessage();
                            message.obj = dataBean;
                            message.what = COMMENTCALLBACK;
                            handler.sendMessage(message);
                        }
                    });
                }

            } else {//评论列表
                //      "courseId：视频ID；content：评论内容
                final FormBody formBody = new FormBody.Builder()
                        .add("courseId", courseId)
                        .add("content", content)
                        .build();

                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        VideoPublishCommentBean videoPublishCommentBean = JsonUitl.videoPublishComment(VideoPlayActivity.this, formBody);
                        Message message = handler.obtainMessage();
                        message.obj = videoPublishCommentBean;
                        message.what = VIDEOCOMMENT;
                        handler.sendMessage(message);
                    }
                });


            }
            editTextInput.setText("");
            editTextInput.setHint("");
            videoInputText.setVisibility(View.GONE);
            videoBottomInput.setVisibility(View.VISIBLE);
            hideInput();
            return true;
        }
        return false;
    }

    public void inputVisible(VideoCommentBean.DataBeanX dataBeanX, Boolean isAllComments, int place, int position) {

        videoBottomInput.setVisibility(View.GONE);
        videoInputText.setVisibility(View.VISIBLE);

        editTextInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) editTextInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        String text = "";
        if (position < 0) {
            text = dataBeanX.getUserName();
        } else {
            text = dataBeanX.getData().get(position).getUserName();
        }

        editTextInput.setHint("回复:@" + text);

        this.dataAllComments = dataBeanX;
        this.isAllComments = isAllComments;
        this.pos = position;
        this.place = place;

    }

    private void dataReady() {

        Intent intent = getIntent();
        courseId = intent.getStringExtra("courseId");

    }

    private void hideInput() {
        methodManager = ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow((VideoPlayActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void VideoViewPageInit(String teacher, String intro) {
        final String[] stringArray = getResources().getStringArray(R.array.sgu_video_tab);
        fragments = new ArrayList<>();

        fragmentVideoComment = new FragmentVideoComment();
        fragmentVideoGave = new FragmentVideoGave();
        fragmentVideoAboutV = new FragmentVideoAboutV();
        fragmentVideoIntroduction = new FragmentVideoIntroduction();


        fragments.add(fragmentVideoComment);
        fragments.add(fragmentVideoGave);
        fragments.add(fragmentVideoIntroduction);
        fragments.add(fragmentVideoAboutV);

        for (int i = 0; i < stringArray.length; i++) {

            Bundle bundle = new Bundle();
            bundle.putString("tab", stringArray[i]);
            bundle.putString("teacherName", data.getTeacherName());
            bundle.putString("teacherFace", data.getTeacherFace());
            bundle.putString("courseId", courseId);
            bundle.putString("teacherId", teacher);
            bundle.putString("intro", intro);
            fragments.get(i).setArguments(bundle);

        }

        videoViewPager.setOffscreenPageLimit(4);

        videoViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return stringArray[position];

            }
        });

        videoTabLayout.setupWithViewPager(videoViewPager);
    }


//    @Override
//    public void finishUpdate(ViewGroup container) {
//        try{
//            super.finishUpdate(container);
//        } catch (NullPointerException nullPointerException){
//            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
//        }
//    }


    public void toastPrompt() {
        Toast.makeText(this, "您不能回复自己", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        activityVideoPlay.setRefreshing(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fragmentVideoGave!=null) {
            fragmentVideoGave.unRegister();
        }
        unregisterReceiver(videoReceiver);
    }

    public void allCommentVisible(List<AllcommentsBean.DataBean> data, int position) {
        allComments = data;
        AllcommentsBean.DataBean dataBean = allComments.get(position);
        allCommentPosition = position;

    }
}

