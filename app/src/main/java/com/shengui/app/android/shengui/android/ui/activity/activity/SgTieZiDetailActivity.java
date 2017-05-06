package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.HtmlHandler;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.CommentListAdapter;
import com.shengui.app.android.shengui.android.ui.view.TieziImagesListAdapter;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.CommentController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CommentModels;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.models.imagesModel;
import com.shengui.app.android.shengui.utils.android.DensityUtil;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.shengui.app.android.shengui.utils.android.ScreenUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by admin on 2017/1/11.
 */

public class SgTieZiDetailActivity extends BaseActivity implements View.OnClickListener, CommentListAdapter.CommomentOnclickListener, ShareOtherPopUpDialog.OnClickLintener {

    TieziImagesListAdapter adapter;   //图片的adapter
    CommentListAdapter commonAdapter; //评论的adapter
    @Bind(R.id.shareImage)
    ImageView shareImage;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.nameTiezaiText)
    TextView nameTiezaiText;
    @Bind(R.id.focusText)
    TextView focusText;
    @Bind(R.id.timeTieZiTextView)
    TextView timeTieZiTextView;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.addressTextView)
    TextView addressTextView;
    @Bind(R.id.topTextView)
    TextView topTextView;
    @Bind(R.id.niceTv)
    ImageView niceTv;
    @Bind(R.id.niceTextView)
    TextView niceTextView;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.sentCodeText)
    TextView sentCodeText;
    @Bind(R.id.inoutnumberTv)
    EditText inoutnumberTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.contentTv)
    TextView contentTv;
    @Bind(R.id.inputLayout)
    RelativeLayout inputLayout;
    @Bind(R.id.niceLayout)
    LinearLayout niceLayout;
    TieZiDetailModel model;
    List<imagesModel> modelImagelist;
    @Bind(R.id.jc_video)
    JCVideoPlayerStandard jcVideo;
    @Bind(R.id.finishLayours)
    LinearLayout finishLayours;
    @Bind(R.id.WebViewPost)
    WebView WebViewPost;
    private int focusItem = -1;
    private final String W_APPID = Constant.WXIDAPP_ID;
    private IWXAPI api;
    private String reviewid;
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    public static Handler htmlHandler;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.shareImage:
                ShareOtherPopUpDialog();
                break;
            case R.id.focusText:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    if (focusText.getText().equals("关注")) {
//                        focusText.setText("取消关注");
                        MineInfoController.getInstance().Attentionadd(this, model.getUser_id(), UserPreference.getTOKEN());
                    } else {
//                        focusText.setText("关注");
                        MineInfoController.getInstance().Attentiondel(this, model.getUser_id(), UserPreference.getTOKEN());
                    }
                } else {

                    IntentTools.startLogin(SgTieZiDetailActivity.this);
                }

                break;
            case R.id.sentCodeText:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    Logger.e("eee" + inoutnumberTv.getHint().toString() + inoutnumberTv.getText() + inoutnumberTv.getText().toString().equals(""));
                    if (!inoutnumberTv.getText().toString().equals("")) {
                        if (inoutnumberTv.getHint().toString().equals("回复楼主")) {
                            CommentController.getInstance().CommomemtPup(this, UserPreference.getTOKEN(), Integer.parseInt(model.getId()), Integer.parseInt(model.getUser_id()), inoutnumberTv.getText().toString());
                        } else {
                            CommentController.getInstance().CommomemtPup(this, UserPreference.getTOKEN(), Integer.parseInt(model.getId()), Integer.parseInt(reviewid), inoutnumberTv.getText().toString());
                        }
                    } else {
                        ToastTool.show("评论的内容不能为空");
                    }
                } else {
                    IntentTools.startLogin(SgTieZiDetailActivity.this);
                }
                break;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        listview.setFocusable(false);
        Memberlistview.setFocusable(false);


        WebViewPost.getSettings().setJavaScriptEnabled(true);
        WebViewPost.requestFocus();
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setUseWideViewPort(true);
        WebViewPost.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebViewPost.getSettings().setLoadWithOverviewMode(true);
        htmlHandler = new HtmlHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixedThreadPool.shutdown();
        htmlHandler.removeCallbacksAndMessages(null);
    }

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, W_APPID, true);
        api.registerApp(W_APPID);
    }

    //分享弹窗
    public void ShareOtherPopUpDialog() {   //弹框
        ShareOtherPopUpDialog PopUpDialogs = new ShareOtherPopUpDialog(this, model);
        PopUpDialogs.listener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    @Override
    protected void initEvent() {
        sentCodeText.setOnClickListener(this);
        focusText.setVisibility(View.GONE);
        focusText.setOnClickListener(this);
        shareImage.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        inoutnumberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inoutnumberTv.setHint("回复楼主");
            }
        });
        niceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    if (model.getIs_dig().equals("0")) {
                        niceTv.setImageDrawable(getResources().getDrawable(R.drawable.like_image_bg));
                        int niceNumber = Integer.parseInt(niceTextView.getText().toString());
                        niceNumber++;
                        niceTextView.setText(niceNumber + "");
                        TieZiController.getInstance().setDig(SgTieZiDetailActivity.this, model.getId(), "set");
                    } else {
                        niceTv.setImageDrawable(getResources().getDrawable(R.drawable.tiezi_like));
                        int niceNumber = Integer.parseInt(niceTextView.getText().toString());
                        if (niceNumber > 0) {
                            niceNumber--;
                            niceTextView.setText(niceNumber + "");
                            TieZiController.getInstance().setDig(SgTieZiDetailActivity.this, model.getId(), "cancel");
                        }
                    }
                    model.setIs_dig(model.getIs_dig().equals("0") ? "1" : "0");
                } else {
                    IntentTools.startLogin(SgTieZiDetailActivity.this);
                }
            }
        });
        finishLayours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        regToWx();
        personInfoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startOtherDetail(SgTieZiDetailActivity.this, model.getUserinfo().getId());
            }
        });
    }

    @Override
    protected void initData() {
        listview.setVisibility(View.GONE);
        jcVideo.setVisibility(View.GONE);
        if (getIntent().getSerializableExtra("pageid") != null) {
            String id = (String) getIntent().getSerializableExtra("pageid");
            Logger.e("pageid" + id);
            TieZiController.getInstance().getNewTieZiDetail(this, id);
            CommentController.getInstance().CommomentList(this, Integer.parseInt(id), 0, 80);
        }
        adapter = new TieziImagesListAdapter(this);
        modelImagelist = new ArrayList<>();
        List<ProductModel> model = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            model.add(new ProductModel());
//        }
//        adapter.setRes(model);
        listview.setAdapter(adapter);

        commonAdapter = new CommentListAdapter(this);
//        commonAdapter.setRes(model);
        Memberlistview.setAdapter(commonAdapter);
        commonAdapter.setListener(this);
//        Memberlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                Logger.e("item"+commonAdapter.getItem(position).toString());
//                inoutnumberTv.setFocusable(true);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                inoutnumberTv.setHint("回复"+commonAdapter.getItem(position).getUser_id());
//            }
//        });
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
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tiezi_detail_activity;
    }


    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.CommentPub.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("评论成功");
                    inoutnumberTv.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inoutnumberTv.getWindowToken(), 0);

                    CommentController.getInstance().CommomentList(this, Integer.parseInt((String) getIntent().getSerializableExtra("pageid")), 0, 10);
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("评论失败");
            }
        }
        if (flag == HttpConfig.CommentGetlist.getType()) {

            Logger.e("result" + result);

            CommentModels models = (CommentModels) result;
            Logger.e("resultmodels" + models.getCount());
            commonAdapter.setRes(models.getResult());
        }

        if (flag == HttpConfig.Attentionadd.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("关注成功");
                    focusText.setText("取消关注");
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("关注失败");
            }
        }

        if (flag == HttpConfig.Attentiondel.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("取消关注成功");
                    focusText.setText("关注");
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("取消失败");
            }
        }

        ImageView imageView;
        if (flag == HttpConfig.get_info.getType()) {

            try {
                JSONObject ja = new JSONObject(o.toString());
                if (ja.getBoolean("status")) {
                    model = (TieZiDetailModel) result;

                    Logger.e("deatat----------------" + model.toString());
                    reviewid = model.getUser_id();
                    Log.e("datyeddd", model.toString());
                    Glide.with(this).load(model.getUserinfo().getAvatar()).centerCrop().error(R.drawable.default_image).into(personInfoIv);
                    nameTiezaiText.setText(model.getUserinfo().getName());
                    timeTieZiTextView.setText(getStrTime(model.getCreate_time()));
                    contentTv.setText(model.getContent());
                    if (model.getCircle() != null) {
                        addressTextView.setText(model.getCircle());
                    } else {
                        addressTextView.setVisibility(View.GONE);
                    }

                    if (model.getTopic() != null && model.getTopic().equals("")) {
                        topTextView.setVisibility(View.GONE);
                    } else {
                        topTextView.setVisibility(View.VISIBLE);
                        topTextView.setText("#" + model.getTopic() + "#");
                    }
                    niceTextView.setText(model.getDig_num());
//                    focusText.setVisibility(View.VISIBLE);
//                    if (model.getIs_attention().equals("0")) {
//                        focusText.setText("关注");
//                    } else {
//                        focusText.setText("取消关注");
//                    }
                    Logger.e("ismaxed-------------------------------" + model.getIs_mixed() + model.getMix_content());
                    if (model.getIs_mixed() != null && model.getIs_mixed().equals("1")) {
                        try {
                            contentTv.setVisibility(View.GONE);
                            listview.setVisibility(View.GONE);
                            jcVideo.setVisibility(View.GONE);
                            WebViewPost.setVisibility(View.VISIBLE);
                            int px = DensityUtil.px2dip(this, ScreenUtils.getScreenWidth(this)) - 80;
                            String str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                                    "<head>  \n" +
                                    "<style>\n" +
                                    "body img{width:" + px + "px!important;margin:0px 0px!important;}\n" +
                                    "\n" +
                                    "</style>\n" +
                                    "</head>\n" +
                                    "<body>";

                            String end = "\n" +
                                    "</body>\n" +
                                    "</html> ";

                            String content = str + model.getMix_content() + end;
                            Logger.e("html-----" + content);
                            WebViewPost.loadDataWithBaseURL("http://api.gui66.com/", content, "text/html", "utf-8", null);
//                            fixedThreadPool.execute(new HtmlRunnable(htmlHandler,model.getMix_content(),this,R.id.WebViewPost));
                        } catch (Exception e) {
                            Logger.e("exception" + e.getMessage());
                        }
                        Logger.e("ismaxed-------wer-----" + model.getMix_content());

//                        WebSettings settings = WebViewPost.getSettings();
//                        settings.setJavaScriptEnabled(true);
//                        String htmlContent = model.getMix_content();
//
//                        htmlContent = htmlContent + "<script type=\"text/javascript\">" +
//                                "var imgs = document.getElementsByTagName('img');" + // 找到table标签
//                                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
//                                "imgs[i].style.width = '100%';" +  // 宽度改为100%
//                                "imgs[i].style.height = 'auto';" +
//                                "}" +
//                                "</script>";
//                        WebViewPost.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
//                        WebViewPost.setText(Html.fromHtml(model.getMix_content()));
                    } else {
                        WebViewPost.setVisibility(View.GONE);
                        if (model.getImages() != null && model.getImages().size() > 0) {
                            listview.setVisibility(View.VISIBLE);
                            modelImagelist.addAll(model.getImages());
                            adapter.setRes(modelImagelist);
                        } else {
                            if (model.getVideo_info() != null) {
                                if (!model.getVideo_info().getUrl().equals("")) {
                                    jcVideo.setVisibility(View.VISIBLE);
                                    jcVideo.setUp(model.getVideo_info().getUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"");
//                                if ((model.getVideo_info().getCover().equals(""))) {
//                                    Picasso.with(this).load(R.drawable.default_pictures).into(jcVideo.thumbImageView);
//                                } else {
//                                    Picasso.with(this).load(model.getVideo_info().getCover()).into(jcVideo.thumbImageView);
//                                }
//                                jcVideo.thumbImageView.setImageBitmap(createVideoThumbnail(model.getVideo_info().getUrl(),500,400));
                                    new MyTask(model.getVideo_info().getUrl(), jcVideo.thumbImageView).execute();
//                            Picasso.with(this)
//                                    .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
//                                    .into(jcVideo.thumbImageView);
                                    JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                                } else {
                                    jcVideo.setVisibility(View.GONE);
                                }
                            }
                        }

                    }
                    Logger.e("isgiggggggg------------------" + Integer.parseInt(model.getIs_dig()));
                    if (Integer.parseInt(model.getIs_dig()) == 0) {
                        niceTv.setImageDrawable(getResources().getDrawable(R.drawable.tiezi_like));
                    } else {
                        niceTv.setImageDrawable(getResources().getDrawable(R.drawable.like_image_bg));

                    }


                } else {
                    ToastTool.show("该帖子已被删除");
                }

            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }
        if (flag == HttpConfig.CommentSet_dig.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("点赞成功");
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("点赞失败");
            }
        }
    }

    @Override
    public void OnclickNice(View v, int position) {

        try {
            if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                Logger.e("dignumber" + commonAdapter.getItem(position).getDig_num() + commonAdapter.getItem(position).getIs_dig().equals("0"));
                int dignumber = Integer.parseInt(commonAdapter.getItem(position).getDig_num());
                if (commonAdapter.getItem(position).getIs_dig().equals("0")) {
                    dignumber++;
                    CommentController.getInstance().CommomentDig(this, UserPreference.getTOKEN(), Integer.parseInt(commonAdapter.getItem(position).getId()), "set");
                } else {
                    dignumber--;
                    CommentController.getInstance().CommomentDig(this, UserPreference.getTOKEN(), Integer.parseInt(commonAdapter.getItem(position).getId()), "cancel");
                }
                commonAdapter.getItem(position).setIs_dig(commonAdapter.getItem(position).getIs_dig().equals("0") ? "1" : "0");
                commonAdapter.getItem(position).setDig_num(dignumber + "");
                commonAdapter.notifyDataSetChanged();

            } else {
                IntentTools.startLogin(SgTieZiDetailActivity.this);
            }
        } catch (Exception e) {
            Logger.e("exception" + e.getMessage());
        }

    }

    @Override
    public void OnclickCommoment(View v, int position) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Logger.e("item" + commonAdapter.getItem(position).toString());
        inoutnumberTv.setFocusable(true);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        inoutnumberTv.setHint("回复" + commonAdapter.getItem(position).getUserinfo().getName());
        reviewid = commonAdapter.getItem(position).getUser_id();
    }

    @Override
    public void OnClickContent(View v, int position) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Logger.e("item" + commonAdapter.getItem(position).toString());
        inoutnumberTv.setFocusable(true);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        inoutnumberTv.setHint("回复" + commonAdapter.getItem(position).getUserinfo().getName());
        reviewid = commonAdapter.getItem(position).getUser_id();
    }


    @Override
    public void OnClick(TieZiDetailModel mo) {
        Logger.e(",odmere---wwwwwwwwwww-");
        if (!api.isWXAppInstalled()) {
            ToastTool.show("您还未安装微信");
            return;
        }
        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = Constant.PostShareUrl + model.getId();
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (mo.getCircle().length() > 14) {
            msg.title = mo.getCircle().substring(0, 14) + "...";
        } else {
            msg.title = mo.getCircle();
        }
        if (mo.getContent().length() > 14) {
            msg.description = mo.getContent().substring(0,14) + "...";
        } else {
            msg.description = mo.getContent();
        }


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;

        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = SendMessageToWX.Req.WXSceneSession;

        api.sendReq(req);
    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        private ImageView imageView;
        private String url;

        private Bitmap bitmap;

        public MyTask(String urls, ImageView imageViews) {

            url = urls;
            imageView = imageViews;
        }

        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {


            bitmap = createVideoThumbnail(url, 200, 160);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imageView.setImageBitmap(bitmap);
        }
    }

    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }
}
