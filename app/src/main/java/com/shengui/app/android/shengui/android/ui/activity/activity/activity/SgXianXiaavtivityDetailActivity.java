package com.shengui.app.android.shengui.android.ui.activity.activity.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.ShareXianXiaActivityOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.HtmlHandler;
import com.shengui.app.android.shengui.android.ui.utilsview.HtmlRunnable;
import com.shengui.app.android.shengui.android.ui.utilsview.ImagePagerView;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityDetailListAdapter;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ActivityModel;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/29.
 */

public class SgXianXiaavtivityDetailActivity extends BaseActivity implements View.OnClickListener, ShareXianXiaActivityOtherPopUpDialog.Listener {
    @Bind(R.id.shareImage)
    ImageView shareImage;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bottomText)
    TextView bottomText;
    @Bind(R.id.pager)
    ImagePagerView pager;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    String modelId;
    ActivityDetailListAdapter adapter;
    @Bind(R.id.contentTv)
    TextView contentTv;
    List<ImageModel> focuslist;
    ActivityModel model;
    private final String W_APPID = Constant.WXIDAPP_ID;
    @Bind(R.id.webView)
    WebView webView;
    private IWXAPI api;
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    public static Handler htmlHandler;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.shareImage:
                ShareOtherPopUpDialog();
                break;
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.bottomText:
                String endtime = model.getEtime() + "000";
                long nowtime = new Date().getTime();
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    if (nowtime > Long.parseLong(endtime)) {
                        ToastTool.show("报名结束");
                    } else {
                        if (bottomText.getText().equals("已经报名")) {
                            ToastTool.show("您已经报名");
                        } else {
                            IntentTools.startSingUpActivity(this, modelId);
                        }
                    }
                } else {

                    IntentTools.startLogin(this);
                }


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixedThreadPool.shutdown();
        htmlHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
//        MineInfoController.getInstance().get_focus_nav(this, UserPreference.getLng(),UserPreference.getLat());
        adapter = new ActivityDetailListAdapter(this);
        htmlHandler = new HtmlHandler(this);
    }

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, W_APPID, true);
        api.registerApp(W_APPID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initEvent() {
        bottomText.setOnClickListener(this);
        shareImage.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
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
//        pager.setOnItemClickLisener(new ImagePagerView.OnItemClickLisener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Logger.e("posito" + position);
//                SkipActivity(focuslist.get(position));
//            }
//        });

        Memberlistview.setAdapter(adapter);
        List<ProductModel> models = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            models.add(new ProductModel());
        }
        adapter.setRes(models);
        regToWx();
    }

    @Override
    protected void initData() {

        if (getIntent() != null) {
            modelId = (String) getIntent().getSerializableExtra("ActivityModel");
            ActivityController.getInstance().ActivityDetail(this, modelId);
            if (getIntent().getSerializableExtra("is") != null) {
                bottomText.setVisibility(View.GONE);
            }
        }
//        contentTv.getSettings().setJavaScriptEnabled(true);
//        contentTv.setWebChromeClient(new WebChromeClient());
//        List<String> paths = new ArrayList<>();
//        paths.add("http://m.easyto.com/m/zhulifuwu_banner.jpg");
//        paths.add("http://m.easyto.com/m/japan/images/banner_3y_new.jpg");
//        paths.add("http://m.easyto.com/m/japan/images/banner_5y_new.jpg");
//        pager.initData(paths);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_xianxia_detail_activity;
    }

    //分享弹窗
    public void ShareOtherPopUpDialog() {   //弹框
        ShareXianXiaActivityOtherPopUpDialog PopUpDialogs = new ShareXianXiaActivityOtherPopUpDialog(this, model);
        PopUpDialogs.setOnListener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        Logger.e("sff" + o.toString());
        if (flag == HttpConfig.ActivityDetails.getType()) {

            model = (ActivityModel) result;
            Logger.e("model" + model.toString());
            webView.loadDataWithBaseURL(null,model.getContent(), "text/html", "utf-8", null);
//            fixedThreadPool.execute(new HtmlRunnable(htmlHandler, model.getContent(), this, R.id.contentTv));
//            contentTv.loadData(model.getContent(), "text/html; charset=UTF-8", null);
//            contentTv.setText(Html.fromHtml(getString(R.string.html_text)));

            if (model.getSlider().size() > 0) {
                List<String> paths = new ArrayList<>();
                for (int i = 0; i < model.getSlider().size(); i++) {
                    Logger.e("sssfocuslists" + model.getSlider().get(i));
                    paths.add(model.getSlider().get(i));
                }
                pager.initData(paths);
            }
            if (model.getApply().equals("1")) {
                bottomText.setText("已经报名");
            } else {
                bottomText.setText("申请报名");
            }
        } else {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(o.toString());
                JSONObject jaData = jsonObject.getJSONObject("data");
                JSONArray focus = jaData.getJSONArray("focus");
                JSONArray nav = jaData.getJSONArray("nav");
                focuslist = GsonTool.jsonToArrayEntity(focus.toString(), ImageModel.class);

                List<String> paths = new ArrayList<>();
                for (int i = 0; i < focuslist.size(); i++) {
                    Logger.e("sssfocuslists" + focuslist.get(i).getImg());
                    paths.add(focuslist.get(i).getImg());
                }
                pager.initData(paths);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    //首页轮播图和四张菱形图的跳转
    public void SkipActivity(ImageModel model) {
        if (model != null) {
            switch (model.getRedirect_type()) {
                case "0":
                    if (!StringTools.isNullOrEmpty(model.getRedirect_url())) {
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
                    IntentTools.startQuanziDetailById(this, model.getRedirect_url());
                    break;
                case "4":
                    IntentTools.startQuanziDetailById(this, model.getRedirect_url());
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
                    IntentTools.startTextView(this, model.getRedirect_url());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onListener() {
        Logger.e("ads-------");
        weiChat(0);
    }

    // 0-分享给朋友  1-分享到朋友圈
    private void weiChat(int flag) {
        if (!api.isWXAppInstalled()) {
            ToastTool.show("您还未安装微信");
            return;
        }

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = Constant.ActivityShareUrl + model.getId();
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = model.getTitle();
        msg.description = "活动分享";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;

        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;

        api.sendReq(req);
    }

}
