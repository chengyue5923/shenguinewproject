package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.ShareGongQiuOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.ImagePagerView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GongQiuController;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.utils.android.EmptyLayout;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.shengui.app.android.shengui.utils.im.CommonUtil;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by admin on 2016/12/16.
 */

public class SgOtherProductDetailActivity extends BaseActivity implements View.OnClickListener ,ShareGongQiuOtherPopUpDialog.OnClickListener{
    @Bind(R.id.collectLayout)
    RelativeLayout collectLayout;
    @Bind(R.id.chatDetail)
    RelativeLayout chatDetail;
    @Bind(R.id.phoneLayout)
    RelativeLayout phoneLayout;
    @Bind(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @Bind(R.id.backImageView)
    ImageView backImageView;
    @Bind(R.id.shareForUser)
    ImageView shareForUser;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.pager)
    ImagePagerView pager;
    @Bind(R.id.detailTitleview)
    TextView detailTitleview;
    @Bind(R.id.productBuyText)
    TextView productBuyText;
    @Bind(R.id.productBuyTypeText)
    TextView productBuyTypeText;
    @Bind(R.id.detailDostanceText)
    TextView detailDostanceText;
    @Bind(R.id.DetailContentTextView)
    TextView DetailContentTextView;
    @Bind(R.id.detailNumberText)
    TextView detailNumberText;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.personInaDetailText)
    TextView personInaDetailText;
    @Bind(R.id.personNameText)
    TextView personNameText;
    @Bind(R.id.PersonInfoAddress)
    TextView PersonInfoAddress;
    @Bind(R.id.PersonInfoPhoneNumber)
    TextView PersonInfoPhoneNumber;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.infoLayout)
    RelativeLayout infoLayout;
    @Bind(R.id.jc_video)
    JCVideoPlayerStandard jcVideo;
    @Bind(R.id.collectimage)
    ImageView collectimage;
    private EmptyLayout mEmptyLayout;
    GongQiuDetailModel model;
    List<String> paths;
    private final String W_APPID= Constant.WXIDAPP_ID;
    private IWXAPI api;
    @Override
    protected void initView() {

        ButterKnife.bind(this);
//        mEmptyLayout = new EmptyLayout(this, scrollView);
    }

    @Override
    protected void initEvent() {
        shareForUser.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        chatDetail.setOnClickListener(this);
        phoneLayout.setOnClickListener(this);
        collectLayout.setOnClickListener(this);
        backImageView.setOnClickListener(this);
    }
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, W_APPID, true);
        api.registerApp(W_APPID);
    }
    @Override
    protected void initData() {

        if (getIntent().getSerializableExtra("ids") != null) {
            String id = (String) getIntent().getSerializableExtra("ids");
            GongQiuController.getInstance().GongQiuDetail(this, id);
        }
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                Logger.e("posito" + position);
                IntentTools.startBigImage(SgOtherProductDetailActivity.this,paths.get(position));

            }
        });
        paths = new ArrayList<>();
        regToWx();
    }

    @Override
    public void onConnectStart() {
        super.onConnectStart();
//        mEmptyLayout.showLoading();
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
//        mEmptyLayout.showError();
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guimi_detail_page;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.getGongQiuDetail.getType()) {
            Logger.e("model;" + result);
//            mEmptyLayout.showSuccess(false);
            model = (GongQiuDetailModel) result;
            Logger.e("modelmodelmodel" + model.toString());
            detailTitleview.setText(model.getTitle());
            DetailContentTextView.setText(model.getContents());
            if (model.getType().equals("1")) {
                productBuyText.setText("出售");
                productBuyText.setBackground(getResources().getDrawable(R.drawable.activity_quanzi_confirm));
            } else {
                productBuyText.setText("求购");
                productBuyText.setBackground(getResources().getDrawable(R.drawable.activity_product_title));
            }
            productBuyTypeText.setText(model.getVariety());
            detailDostanceText.setText(model.getCity_name());
            detailNumberText.setText("浏览：" + model.getView_num() + "次");
            PersonInfoAddress.setText(model.getProvince());
            PersonInfoPhoneNumber.setText(model.getContact_mobile());
            personNameText.setText(model.getUserinfo().getName());
            try {
                if (!model.getUserinfo().getAvatar().equals("")) {
                    Glide.with(this).load(model.getUserinfo().getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
                } else {
                    Glide.with(this).load(R.drawable.default_image).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
                }
                if(model.getIs_favorite().equals("0")){
                    //为收藏
                    collectimage.setImageDrawable(getResources().getDrawable(R.drawable.product_collection_img));
                }else{
                    //为收藏
                    collectimage.setImageDrawable(getResources().getDrawable(R.drawable.save_collection_img));
                }
            } catch (Exception e) {
                Logger.e("sd" + e.getMessage());
            }

            paths.clear();

            pager.setVisibility(View.GONE);
            jcVideo.setVisibility(View.GONE);
            if (model != null && model.getImages().size() > 0) {
                pager.setVisibility(View.VISIBLE);
//                paths.add(model.getCover());
//                paths.add(model.getCover_original());
                for (ImageModel m : model.getImages()) {
                    paths.add(m.getUrl_middle());
                }
                pager.initData(paths);
            } else {
                try {
                    if (model.getVideo_info() != null) {
                        if (!model.getVideo_info().getUrl().equals("")) {
                            jcVideo.setVisibility(View.VISIBLE);
                            jcVideo.setUp(model.getVideo_info().getUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
//                            if ((model.getVideo_info().getCover().equals(""))) {
//                                Picasso.with(this).load(R.drawable.default_pictures).into(jcVideo.thumbImageView);
//                            } else {
//                                Picasso.with(this).load(model.getVideo_info().getCover()).into(jcVideo.thumbImageView);
//                            }
                            jcVideo.thumbImageView.setImageBitmap(createVideoThumbnail(model.getVideo_info().getUrl(),500,400));
                            JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                        } else {
                            jcVideo.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    Logger.e("e.get" + e.getMessage());
                }
            }
        }
        if (flag == HttpConfig.Favoriteadd.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
                    ToastTool.show("收藏成功");
                    collectimage.setImageDrawable(getResources().getDrawable(R.drawable.save_collection_img));
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {
            }
        }
        if (flag == HttpConfig.Favoritedel.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
                    ToastTool.show("取消收藏");
                    collectimage.setImageDrawable(getResources().getDrawable(R.drawable.product_collection_img));
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {

            }
        }
    }

    public void ShareOtherPopUpDialog() {   //弹框
        ShareGongQiuOtherPopUpDialog PopUpDialogs = new ShareGongQiuOtherPopUpDialog(this, model);
        PopUpDialogs.setListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImageView:
                finish();
                break;
            case R.id.collectLayout:
                Logger.e("token"+UserPreference.getTOKEN());
                if (!UserPreference.getTOKEN().equals("")) {
                    Logger.e("user" + model.getUser_id());
                    if (model.getIs_favorite().equals("0")) {
                        GuiMiController.getInstance().Favoriteadd(this, UserPreference.getTOKEN(), model.getId(), "supply");
                    } else {
                        GuiMiController.getInstance().Favoritedel(this, UserPreference.getTOKEN(), model.getId(), "supply");
                    }
                    model.setIs_favorite(model.getIs_favorite().equals("0")?"1":"0");
                } else {
                    ToastTool.show("您还没有登录");
                }
                break;
            case R.id.phoneLayout:
                CommonUtil.call(SgOtherProductDetailActivity.this, model.getContact_mobile());
                break;
            case R.id.chatDetail:
                if (UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1) {
                    Logger.e("user" + model.getUser_id());
                    IntentTools.startChat(this, Integer.parseInt(model.getUser_id()),model.getUserinfo().getName(),model.getAvatar());
                } else {
                    ToastTool.show("您还没有登录");
                }
                break;
            case R.id.infoLayout:
                IntentTools.startOtherDetail(this, model.getUserinfo().getId());
                break;
            case R.id.shareForUser:
                ShareOtherPopUpDialog();
                break;
        }
    }

    @Override
    public void Listener() {
// 0-分享给朋友  1-分享到朋友圈
            if (!api.isWXAppInstalled()) {
                ToastTool.show("您还未安装微信");
                return;
            }
            //创建一个WXWebPageObject对象，用于封装要发送的Url
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl =Constant.GongQiuShareUrl+model.getId();
            //创建一个WXMediaMessage对象
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title ="神龟网";
            msg.description ="神龟网供求分享";

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
            req.message = msg;

            //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
            req.scene =  SendMessageToWX.Req.WXSceneSession ;

            api.sendReq(req);
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
